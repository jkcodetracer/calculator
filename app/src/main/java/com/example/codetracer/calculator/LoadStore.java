package com.example.codetracer.calculator;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by codetracer on 3/28/17.
 */

public class LoadStore {
    public static boolean save(Context context, String fileName, Stack<String> list) {
        String xmlWriter = null;
        FileOutputStream outStream;
        Log.d("begin save", "save");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder;
        try {
            outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            /*
            for (String x:list) {

                outStream.write(x.getBytes());
            }
            outStream.close();
            */
            ////
            dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.newDocument();
            Element root = doc.createElement("root");

            doc.appendChild(root);
            for (String x:list) {
                Node node = doc.createElement("Replay");
                Text expression = doc.createTextNode(x);
                node.appendChild(expression);
                root.appendChild(node);
            }

            // save the xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc.getDocumentElement());
            OutputStream output = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(output);
            transformer.transform(source, result);

            xmlWriter = output.toString();
            Log.d("save", xmlWriter);
            outStream.write(xmlWriter.getBytes());
            outStream.close();

            return true;
        } catch (Exception e) {
            Log.d("LS", "Problem saving " + fileName);
        }
        return false;
    }


    public static Stack<String> load(Context context, String fileName) {
        FileInputStream inStream;
        String xmlreader;
        Stack<String> list = new Stack<String>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // load the xml tree
            inStream = context.openFileInput(fileName);
            byte[] temp = new byte[1024];
            int len = 0;

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inStream);
            NodeList root;

            root = doc.getElementsByTagName("root");
            for (int i = 0; i < root.getLength(); i++) {
                Log.d("load ", root.item(i).getTextContent());
                list.push(root.item(i).getTextContent());
            }
            inStream.close();
        } catch (Exception e) {
            return null;

        }
        return list;
    }
}


