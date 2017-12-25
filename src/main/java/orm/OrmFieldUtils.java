package orm;

import org.apache.commons.lang3.reflect.FieldUtils;
import orm.annotations.Column;
import orm.annotations.Entity;
import orm.fields.OrmField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class OrmFieldUtils {
    public static Map<String, Field> getRelationalToObjectMapping(Class<? extends Model> clazz) {
        Map<String, Field> ret = new HashMap<>();
        FieldUtils.getAllFieldsList(clazz).stream().filter(
                f -> OrmField.class.isAssignableFrom(f.getType())
        ).forEach(
                f -> ret.put(chooseNameForField(f), f)
        );
        return ret;
    }

    public static Map<Field, Integer> getUpdateMapping(Class<? extends Model> clazz) {
        Map<Field, Integer> ret = new HashMap<>();
        FieldUtils.getAllFieldsList(clazz).stream().filter(
                f -> OrmField.class.isAssignableFrom(f.getType()) && !f.getName().equalsIgnoreCase("id")

        ).forEach(
                f -> ret.put(f, (ret.size() + 1))
        );
        return ret;
    }

    public static Map<Field, String> getObjectToRelationalMapping(Class<? extends Model> clazz) {
        Map<Field, String> ret = new HashMap<>();
        FieldUtils.getAllFieldsList(clazz).stream().filter(
                f -> OrmField.class.isAssignableFrom(f.getType())

        ).forEach(
                f -> ret.put(f, chooseNameForField(f))
        );
        return ret;
    }

    public static String getTableName(Class<? extends Model> clazz){
        return clazz.getAnnotation(Entity.class).table();
    }

    private static String chooseNameForField(Field field){
        Column columnAnnotation = field.getAnnotation(Column.class);
        return columnAnnotation == null ? field.getName() : columnAnnotation.name();
    }
}
