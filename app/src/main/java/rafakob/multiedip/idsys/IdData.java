package rafakob.multiedip.idsys;

public class IdData {
    private double[] input;
    private double[] output;
    private double ts;
    private long length;
    private String type;
    private String path;

    public IdData() {
    }

    public IdData(double[] output, double ts) {
        // Timeseries
        this.output = output;
        this.ts = ts;
        this.length = output.length;
    }

    public IdData(double[] output, double[] input, double ts) {
        // SISO
        this.input = input;
        this.output = output;
        this.ts = ts;
        this.length = output.length;
    }

    public void saveToFile(String pathToFile){
        // TODO: zaimplementować
    }


//    public void copy(IdData objToCopy) {
//        this.input = objToCopy.getInput();
//        this.output = objToCopy.getOutput();
//        this.ts = objToCopy.getTs();
//        this.length = objToCopy.getLength();
//        this.type = objToCopy.getType();
//        this.path = objToCopy.getPath();
//    }

    public double[] getInput() {
        return input;
    }

    public void setInput(double[] input) {
        this.input = input;
    }

    public double[] getOutput() {
        return output;
    }

    public void setOutput(double[] output) {
        this.output = output;
    }

    public double getTs() {
        return ts;
    }

    public void setTs(double ts) {
        this.ts = ts;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
