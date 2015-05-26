package rafakob.multiedip.idsys;

public class IdData {
    private double[] input;
    private double[] output;
    private double[] samples;
    private double ts = 1;
    private int length = 0;
    private String type = "";
    private String path;


    public IdData() {
    }

    public IdData(double[] output, double ts) {
        // Timeseries
        this.output = output;
        this.ts = ts;
        this.length = output.length;

        initSamples();
    }

    public IdData(double[] output, double[] input, double ts) {
        // SISO
        this.input = input;
        this.output = output;
        this.ts = ts;
        this.length = output.length;

        initSamples();
    }

    public void cloneFromIddata(IdData iddata){
        this.length = iddata.getLength();
        this.type = iddata.getType();
        this.path = iddata.getPath();
        this.ts = iddata.getTs();
        this.input = iddata.getInput();
        this.output = iddata.getOutput();
        this.samples = iddata.getSamples();
    }

    public void saveToFile(String pathToFile){
        // TODO: zaimplementować
    }

    public void initSamples(){
        samples = new double[length];
        for (int i = 0; i < length; i++)
            samples[i] = i*ts;
    }


    public boolean isNull(){
        return length < 1;
    }
    public boolean isTimeseries(){
        return type.equals("timeseries");
    }
    public boolean isSiso(){
        return type.equals("siso");
    }



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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
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

    public double[] getSamples() {
        return samples;
    }

    public void setSamples(double[] samples) {
        this.samples = samples;
    }
}
