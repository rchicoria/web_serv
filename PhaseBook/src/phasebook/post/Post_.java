package phasebook.post;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-30T01:48:33.245+0000")
@StaticMetamodel(Post.class)
public class Post_ {
	public static volatile SingularAttribute<Post, Integer> id;
	public static volatile SingularAttribute<Post, Integer> photo_id;
	public static volatile SingularAttribute<Post, String> text;
	public static volatile SingularAttribute<Post, Boolean> private_;
	public static volatile SingularAttribute<Post, Boolean> read_;
	public static volatile SingularAttribute<Post, Timestamp> createdAt;
	public static volatile SingularAttribute<Post, Timestamp> deletedAt;
	public static volatile SingularAttribute<Post, Integer> fromUserId;
	public static volatile SingularAttribute<Post, Integer> toUserId;
}
