package phasebook.post;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import phasebook.photo.Photo;
import phasebook.user.PhasebookUser;

@Generated(value="Dali", date="2011-11-23T22:20:40.141+0000")
@StaticMetamodel(Post.class)
public class Post_ {
	public static volatile SingularAttribute<Post, Integer> id;
	public static volatile SingularAttribute<Post, PhasebookUser> fromUser;
	public static volatile SingularAttribute<Post, PhasebookUser> toUser;
	public static volatile SingularAttribute<Post, Boolean> private_;
	public static volatile SingularAttribute<Post, Boolean> read_;
	public static volatile SingularAttribute<Post, Timestamp> createdAt;
	public static volatile SingularAttribute<Post, Timestamp> deletedAt;
	public static volatile SingularAttribute<Post, Photo> photo;
	public static volatile SingularAttribute<Post, String> text;
}
