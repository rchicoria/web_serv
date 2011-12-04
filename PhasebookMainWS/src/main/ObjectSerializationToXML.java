package main;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
 
public class ObjectSerializationToXML {
 
    /**
     * This method saves (serialized) any java bean object into xml file
     */
    public static byte[] serializeObjectToXML(Object objectToSerialize){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(os);
        encoder.writeObject(objectToSerialize);
        return os.toByteArray();
    }
 
    /**
     * Reads Java Bean Object From XML File
     */
    public static HashMap<String, Object> deserializeXMLToObject(byte[] entry) {
    	ByteArrayInputStream os = new ByteArrayInputStream(entry);
        XMLDecoder decoder = new XMLDecoder(os);
        HashMap<String,Object> deSerializedObject = (HashMap<String, Object>) decoder.readObject();
        decoder.close();
 
        return deSerializedObject;
    }
}