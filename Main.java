
public class Main {
public static void main(String []args) {
	String path = "d:\\sample4.csv";
	CSVfile csvfile = CSVfile.createCSVfile(path);
	csvfile.splitFile(path, 3);
}

}
