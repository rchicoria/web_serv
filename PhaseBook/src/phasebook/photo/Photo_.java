package phasebook.photo;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-23T22:19:49.296+0000")
@StaticMetamodel(Photo.class)
public class Photo_ {
	public static volatile SingularAttribute<Photo, Integer> id;
	public static volatile SingularAttribute<Photo, String> name;
	public static volatile SingularAttribute<Photo, Timestamp> createdAt;
	public static volatile SingularAttribute<Photo, Timestamp> deletedAt;
	public static volatile SingularAttribute<Photo, String> label;
}
