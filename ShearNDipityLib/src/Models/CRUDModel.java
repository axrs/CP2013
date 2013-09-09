package Models;

import Interfaces.BaseModel;

public interface CRUDModel extends BaseModel {
    public boolean create();

    public boolean retrieve(int id);

    public boolean retrieve();

    public boolean update();

    public boolean delete();
}
