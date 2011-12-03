
package client.artefact;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client.artefact package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _LoginUser_QNAME = new QName("http://main/", "loginUser");
    private final static QName _GetPosts_QNAME = new QName("http://main/", "getPosts");
    private final static QName _CreateUserResponse_QNAME = new QName("http://main/", "createUserResponse");
    private final static QName _LoginUserResponse_QNAME = new QName("http://main/", "loginUserResponse");
    private final static QName _GetPostsResponse_QNAME = new QName("http://main/", "getPostsResponse");
    private final static QName _CreateUser_QNAME = new QName("http://main/", "createUser");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.artefact
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPostsResponse }
     * 
     */
    public GetPostsResponse createGetPostsResponse() {
        return new GetPostsResponse();
    }

    /**
     * Create an instance of {@link PostDetailsInfo }
     * 
     */
    public PostDetailsInfo createPostDetailsInfo() {
        return new PostDetailsInfo();
    }

    /**
     * Create an instance of {@link LoginUser }
     * 
     */
    public LoginUser createLoginUser() {
        return new LoginUser();
    }

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link PostsContainer }
     * 
     */
    public PostsContainer createPostsContainer() {
        return new PostsContainer();
    }

    /**
     * Create an instance of {@link GetPosts }
     * 
     */
    public GetPosts createGetPosts() {
        return new GetPosts();
    }

    /**
     * Create an instance of {@link AuthInfo }
     * 
     */
    public AuthInfo createAuthInfo() {
        return new AuthInfo();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link LoginUserResponse }
     * 
     */
    public LoginUserResponse createLoginUserResponse() {
        return new LoginUserResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://main/", name = "loginUser")
    public JAXBElement<LoginUser> createLoginUser(LoginUser value) {
        return new JAXBElement<LoginUser>(_LoginUser_QNAME, LoginUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPosts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://main/", name = "getPosts")
    public JAXBElement<GetPosts> createGetPosts(GetPosts value) {
        return new JAXBElement<GetPosts>(_GetPosts_QNAME, GetPosts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://main/", name = "createUserResponse")
    public JAXBElement<CreateUserResponse> createCreateUserResponse(CreateUserResponse value) {
        return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME, CreateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://main/", name = "loginUserResponse")
    public JAXBElement<LoginUserResponse> createLoginUserResponse(LoginUserResponse value) {
        return new JAXBElement<LoginUserResponse>(_LoginUserResponse_QNAME, LoginUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPostsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://main/", name = "getPostsResponse")
    public JAXBElement<GetPostsResponse> createGetPostsResponse(GetPostsResponse value) {
        return new JAXBElement<GetPostsResponse>(_GetPostsResponse_QNAME, GetPostsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://main/", name = "createUser")
    public JAXBElement<CreateUser> createCreateUser(CreateUser value) {
        return new JAXBElement<CreateUser>(_CreateUser_QNAME, CreateUser.class, null, value);
    }

}
