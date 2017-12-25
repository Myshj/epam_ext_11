package views;

import orm.Model;

import java.util.List;

public class ModelListView<T extends Model> {
    private String header;
    private List<T> list;

    public ModelListView(
            String header,
            List<T> list
    ) {
        this.header = header;
        this.list = list;
    }

    public void display(){
        System.out.println(header);
        list.forEach(
                System.out::println
        );
    }
}
