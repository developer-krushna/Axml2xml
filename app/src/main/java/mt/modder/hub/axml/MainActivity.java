package mt.modder.hub.axml;

import android.app.*;
import android.os.*;
import android.widget.*;
import java.io.*;
import java.nio.charset.*;

public class MainActivity extends Activity {

	public String Input_Path = "/storage/emulated/0/decompiled.xml";

	public String outPath = "sdcard/AndroidManifest.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		final ScrollView scroll = new ScrollView(this);	
		scroll.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
		scroll.setFillViewport(true);
		scroll.setPadding(8,8,8,8);
		TextView text = new TextView(this);
		text.setText("Processing "+ Input_Path +" ...");
		try {
            // Read the binary XML file into a byte array
            

			// initialize the axmlprinter class
			AXMLCompiler axmlCompiler = new AXMLCompiler();
            // Use the XMLDecompiler to decompile to an XML string
			// Place your resources.arsc file in the same directory of your xml file
            byte[] xmlString = axmlCompiler.axml2Xml(this, readFile(Input_Path));

			// Direct process without enabling custom resource id2name
			// String xmlString = axmlPrinter.convertXml(byteArray);

            // Output the XML string
            saveAsFile(xmlString, outPath);
			text.setText("Processing complete .File saved in " + outPath);
        } catch (Exception e) {
			// Complete extraction of error 
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String exceptionDetails = sw.toString();
			text.setText(e.toString());
            e.printStackTrace();
        }


		scroll.addView(text);
        setContentView(scroll);
    }
	
	public static String readFile(String path) {

        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        try {
            fr = new FileReader(new File(path));

            char[] buff = new char[1024];
            int length = 0;

            while ((length = fr.read(buff)) > 0) {
                sb.append(new String(buff, 0, length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

	public void saveAsFile(byte[] data, String path) throws IOException{
		File outputFile = new File(path);
		FileOutputStream fos = new FileOutputStream(outputFile);
        fos.write(data);
        fos.close();
	}


}

