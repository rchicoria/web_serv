
package client.artefact;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "Methods", targetNamespace = "http://main/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Methods {


    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns client.artefact.AuthInfo
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createUser", targetNamespace = "http://main/", className = "client.artefact.CreateUser")
    @ResponseWrapper(localName = "createUserResponse", targetNamespace = "http://main/", className = "client.artefact.CreateUserResponse")
    public AuthInfo createUser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        long arg3);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns client.artefact.PostsContainer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPosts", targetNamespace = "http://main/", className = "client.artefact.GetPosts")
    @ResponseWrapper(localName = "getPostsResponse", targetNamespace = "http://main/", className = "client.artefact.GetPostsResponse")
    public PostsContainer getPosts(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        int arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        long arg5);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns client.artefact.AuthInfo
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "loginUser", targetNamespace = "http://main/", className = "client.artefact.LoginUser")
    @ResponseWrapper(localName = "loginUserResponse", targetNamespace = "http://main/", className = "client.artefact.LoginUserResponse")
    public AuthInfo loginUser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        long arg2);

}
