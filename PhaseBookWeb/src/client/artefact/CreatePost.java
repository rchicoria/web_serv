
package client.artefact;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createPost complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createPost">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="arg1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="arg2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg5" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="arg6" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="arg7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg8" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createPost", propOrder = {
    "arg0",
    "arg1",
    "arg2",
    "arg3",
    "arg4",
    "arg5",
    "arg6",
    "arg7",
    "arg8"
})
public class CreatePost {

    protected int arg0;
    protected int arg1;
    protected String arg2;
    protected String arg3;
    protected String arg4;
    protected long arg5;
    protected int arg6;
    protected String arg7;
    protected long arg8;

    /**
     * Gets the value of the arg0 property.
     * 
     */
    public int getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     */
    public void setArg0(int value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     */
    public int getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     */
    public void setArg1(int value) {
        this.arg1 = value;
    }

    /**
     * Gets the value of the arg2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * Sets the value of the arg2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg2(String value) {
        this.arg2 = value;
    }

    /**
     * Gets the value of the arg3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg3() {
        return arg3;
    }

    /**
     * Sets the value of the arg3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg3(String value) {
        this.arg3 = value;
    }

    /**
     * Gets the value of the arg4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg4() {
        return arg4;
    }

    /**
     * Sets the value of the arg4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg4(String value) {
        this.arg4 = value;
    }

    /**
     * Gets the value of the arg5 property.
     * 
     */
    public long getArg5() {
        return arg5;
    }

    /**
     * Sets the value of the arg5 property.
     * 
     */
    public void setArg5(long value) {
        this.arg5 = value;
    }

    /**
     * Gets the value of the arg6 property.
     * 
     */
    public int getArg6() {
        return arg6;
    }

    /**
     * Sets the value of the arg6 property.
     * 
     */
    public void setArg6(int value) {
        this.arg6 = value;
    }

    /**
     * Gets the value of the arg7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg7() {
        return arg7;
    }

    /**
     * Sets the value of the arg7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg7(String value) {
        this.arg7 = value;
    }

    /**
     * Gets the value of the arg8 property.
     * 
     */
    public long getArg8() {
        return arg8;
    }

    /**
     * Sets the value of the arg8 property.
     * 
     */
    public void setArg8(long value) {
        this.arg8 = value;
    }

}
