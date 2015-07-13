package rafakob.sysidmath.sysid.processing;

import org.ejml.alg.dense.linsol.AdjustableLinearSolver;
import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.LinearSolverFactory;

import rafakob.sysidmath.sysid.IdData;
import rafakob.sysidmath.sysid.MathDbl;

public class PolynomialTrendRemoval implements DataProcessingInterface {
    private DenseMatrix64F A; // Vandermonde matrix
    private DenseMatrix64F coef; // matrix containing computed polynomial coefficients
    private DenseMatrix64F y; // observation matrix
    private AdjustableLinearSolver solver;

    private double[] poly; // vector of polynomial values computed for the coef
    private double[] c; // same as 'coef'
    private int N; // number of samples
    private int order;

    public PolynomialTrendRemoval(int order) {
        this.order = order;
    }

    @Override
    public IdData execute(IdData iddata) {
        N = iddata.getLength();
        poly = new double[N];


        coef = new DenseMatrix64F(order+1,1);
        A = new DenseMatrix64F(1,order+1);
        y = new DenseMatrix64F(1,1);
        // create a solver that allows elements to be added or removed efficiently
        solver = LinearSolverFactory.adjustable();

        // generate samples: 1:1:N
        double[] samples = new double[N];
        for (int i = 0; i < N; i++) {
            samples[i] = i;
        }

        polyfit(samples, iddata.getOutput()); // compute coefs and polyval

        // detrend:
        iddata.setOutput(MathDbl.subtractVectors(iddata.getOutput(), poly));
        System.out.printf(MathDbl.getString(iddata.getOutput()));
        return iddata;
    }

    @Override
    public String getFunctionDescription() {
        return "Remove polynomial trend: " + order + " order";
    }


    public void polyfit( double samplePoints[] , double[] observations ) {
        // Create a copy of the observations and put it into a matrix
        y.reshape(observations.length,1,false);
        System.arraycopy(observations,0, y.data,0,observations.length);

        // reshape the matrix to avoid unnecessarily declaring new memory
        // save values is set to false since its old values don't matter
        A.reshape(y.numRows, coef.numRows,false);

        // set up the A matrix
        for( int i = 0; i < observations.length; i++ ) {

            double obs = 1;

            for( int j = 0; j < coef.numRows; j++ ) {
                A.set(i,j,obs);
                obs *= samplePoints[i];
            }
        }

        // process the A matrix and see if it failed
        if( !solver.setA(A) )
            throw new RuntimeException("Solver failed");

        // solver the coefficients
        solver.solve(y, coef);
        c = coef.data.clone();

        // polynomial values:
        for (int i = 0; i < samplePoints.length; i++) {
            poly[i] = polyval(order, c, samplePoints[i]);
        }

    }

    public double polyval(int order, double p[], double x)
    {
        // return y = p(x)
        // using Horner's rule
        double y;
        y = p[order]*x; // p one larger than order
        for(int i=order-1; i>0; i--)
            y = (p[i]+y)*x;
        y = p[0]+y;
        return y;
    }
}
