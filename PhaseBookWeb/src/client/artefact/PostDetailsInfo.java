
package client.artefact;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for postDetailsInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="postDetailsInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="postId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="postPhotoId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="postPhotoName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postPrivate" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="postText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userPhotoId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userPhotoName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "postDetailsInfo", propOrder = {
    "postId",
    "postPhotoId",
    "postPhotoName",
    "postPrivate",
    "postText",
    "userId",
    "userName",
    "userPhotoId",
    "userPhotoName"
})
public class PostDetailsInfo {

    protected int postId;
    protected int postPhotoId;
    protected String postPhotoName;
    protected boolean postPrivate;
    protected String postText;
    protected int userId;
    protected String userName;
    protected int userPhotoId;
    protected String userPhotoName;

    /**
     * Gets the value of the postId property.
     * 
     */
    public int getPostId() {
        return postId;
    }

    /**
     * Sets the value of the postId property.
     * 
     */
    public void setPostId(int value) {
        this.postId = value;
    }

    /**
     * Gets the value of the postPhotoId property.
     * 
     */
    public int getPostPhotoId() {
        return postPhotoId;
    }

    /**
     * Sets the value of the postPhotoId property.
     * 
     */
    public void setPostPhotoId(int value) {
        this.postPhotoId = value;
    }

    /**
     * Gets the value of the postPhotoName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostPhotoName() {
        return postPhotoName;
    }

    /**
     * Sets the value of the postPhotoName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostPhotoName(String value) {
        this.postPhotoName = value;
    }

    /**
     * Gets the value of the postPrivate property.
     * 
     */
    public boolean isPostPrivate() {
        return postPrivate;
    }

    /**
     * Sets the value of the postPrivate property.
     * 
     */
    public void setPostPrivate(boolean value) {
        this.postPrivate = value;
    }

    /**
     * Gets the value of the postText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostText() {
        return postText;
    }

    /**
     * Sets the value of the postText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostText(String value) {
        this.postText = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     */
    public void setUserId(int value) {
        this.userId = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the userPhotoId property.
     * 
     */
    public int getUserPhotoId() {
        return userPhotoId;
    }

    /**
     * Sets the value of the userPhotoId property.
     * 
     */
    public void setUserPhotoId(int value) {
        this.userPhotoId = value;
    }

    /**
     * Gets the value of the userPhotoName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPhotoName() {
        return userPhotoName;
    }

    /**
     * Sets the value of the userPhotoName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPhotoName(String value) {
        this.userPhotoName = value;
    }

}
