package com.example.autoparts;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyDbHelper {

    SQLiteDatabase database;

    private final String createPartsQuery= "Create table if not exists Parts(" +
            "Part_Id integer primary key AUTOINCREMENT, " +
            "Part_Category nvarchar(255) not null, " +
            "Part_Name nvarchar(255) not null, " +
            "Part_Quantity int not null, " +
            "Part_Price decimal(19,2) not null" +
            ")";


    private final String createBillsQuery = "Create Table if not exists Bills(" +
            "Bill_Id integer primary key AUTOINCREMENT, " +
            "Client_Name nvarchar(255) not null, " +
            "Client_Address nvarchar(255) not null, " +
            "Bill_Quantity int not null, " +
            "Bill_Price decimal(19,2) not null, " +
            "Part_Id int not null, " +
            "foreign key(Part_Id) references Parts(Part_Id) " +
            ")";

    public MyDbHelper(MainActivity context) throws SQLException {
        database=SQLiteDatabase.openOrCreateDatabase(
                context.getFilesDir()+"/"+"AutoParts.db", null);
    }

    public MyDbHelper(AddParts context) throws SQLException {
        database=SQLiteDatabase.openOrCreateDatabase(
                context.getFilesDir()+"/"+"AutoParts.db", null);
    }

    public MyDbHelper(Inventory context) throws SQLException {
        database=SQLiteDatabase.openOrCreateDatabase(
                context.getFilesDir()+"/"+"AutoParts.db", null);
    }

    public MyDbHelper(UpdatePart context) throws SQLException {
        database=SQLiteDatabase.openOrCreateDatabase(
                context.getFilesDir()+"/"+"AutoParts.db", null);
    }

    public MyDbHelper(Market context) throws SQLException {
        database=SQLiteDatabase.openOrCreateDatabase(
                context.getFilesDir()+"/"+"AutoParts.db", null);
    }

    public MyDbHelper(AllBills context) throws SQLException {
        database=SQLiteDatabase.openOrCreateDatabase(
                context.getFilesDir()+"/"+"AutoParts.db", null);
    }

    public MyDbHelper(UpdateBill context) throws SQLException {
        database=SQLiteDatabase.openOrCreateDatabase(
                context.getFilesDir()+"/"+"AutoParts.db", null);
    }

    public void createTables() throws SQLException{
        database.execSQL(createPartsQuery);
        database.execSQL(createBillsQuery);
    }

    public ArrayList<String> selectParts() throws SQLException {
        List<String> list = new ArrayList<String>();
        database.beginTransaction();
        String q = "Select Part_Id, Part_Category, Part_Name, Part_Quantity, Part_Price from Parts " +
                "Order By Part_Category";
        Cursor cursor = database.rawQuery(q, null);
        while(cursor.moveToNext()){
            int Part_Id = cursor.getInt(cursor.getColumnIndex("Part_Id"));
            String Part_Category = cursor.getString(cursor.getColumnIndex("Part_Category"));
            String Part_Name = cursor.getString(cursor.getColumnIndex("Part_Name"));
            int Part_Quantity = cursor.getInt(cursor.getColumnIndex("Part_Quantity"));
            float Part_Price = cursor.getFloat(cursor.getColumnIndex("Part_Price"));
            String elements = Part_Id + "\t"
                    + Part_Category + "\t"
                    + Part_Name + "\t"
                    + Part_Quantity + "\t"
                    + Part_Price;
            list.add(elements);
        }
        database.endTransaction();
        return (ArrayList<String>) list;
    }

    public String selectParts(int partId) throws SQLException {
        String elements = "";
        database.beginTransaction();
        String q = "Select Part_Id, Part_Category, Part_Name, Part_Quantity, Part_Price from Parts " +
                "Where Part_Id = " + partId;
        Cursor cursor = database.rawQuery(q, null);
        while(cursor.moveToNext()){
            int Part_Id = cursor.getInt(cursor.getColumnIndex("Part_Id"));
            String Part_Category = cursor.getString(cursor.getColumnIndex("Part_Category"));
            String Part_Name = cursor.getString(cursor.getColumnIndex("Part_Name"));
            int Part_Quantity = cursor.getInt(cursor.getColumnIndex("Part_Quantity"));
            float Part_Price = cursor.getFloat(cursor.getColumnIndex("Part_Price"));
            elements = Part_Id + "\t"
                    + Part_Category + "\t"
                    + Part_Name + "\t"
                    + Part_Quantity + "\t"
                    + Part_Price;
        }
        database.endTransaction();
        return elements;
    }

    public ArrayList<String> selectPartByCategory(String category) throws SQLException {
        List<String> list = new ArrayList<String>();
        database.beginTransaction();
        String q = "Select Part_Id, Part_Category, Part_Name, Part_Quantity, Part_Price from Parts " +
                "Where Part_Category = '"+ category +"' "+
                "Order By Part_Name";
        Cursor cursor = database.rawQuery(q, null);
        while(cursor.moveToNext()){
            int Part_Id = cursor.getInt(cursor.getColumnIndex("Part_Id"));
            String Part_Category = cursor.getString(cursor.getColumnIndex("Part_Category"));
            String Part_Name = cursor.getString(cursor.getColumnIndex("Part_Name"));
            int Part_Quantity = cursor.getInt(cursor.getColumnIndex("Part_Quantity"));
            float Part_Price = cursor.getFloat(cursor.getColumnIndex("Part_Price"));
            String elements = Part_Id + "\t"
                    + Part_Category + "\t"
                    + Part_Name + "\t"
                    + Part_Quantity + "\t"
                    + Part_Price;
            list.add(elements);
        }
        database.endTransaction();
        return (ArrayList<String>) list;
    }

    public ArrayList<String> selectBills() throws SQLException{
        List<String> list = new ArrayList<>();
        database.beginTransaction();
        String q = "Select b.Bill_Id, b.Client_Name, b.Client_Address, p.Part_Name, b.Bill_Quantity, b.Bill_Price  from Bills b " +
                "Inner Join Parts p ON " +
                "b.Part_Id = p.Part_Id " +
                "Order By Client_Name";
        Cursor cursor = database.rawQuery(q, null);
        while(cursor.moveToNext()){
            int Bill_Id = cursor.getInt(cursor.getColumnIndex("Bill_Id"));
            String Client_Name = cursor.getString(cursor.getColumnIndex("Client_Name"));
            String Client_Address = cursor.getString(cursor.getColumnIndex("Client_Address"));
            String Part_Name = cursor.getString(cursor.getColumnIndex("Part_Name"));
            int Bill_Quantity = cursor.getInt(cursor.getColumnIndex("Bill_Quantity"));
            float Bill_Price = cursor.getFloat(cursor.getColumnIndex("Bill_Price"));
            String elements = Bill_Id + "\t"
                    + Client_Name + "\t"
                    + Client_Address + "\t"
                    + Part_Name + "\t"
                    + Bill_Quantity + "\t"
                    + Bill_Price;
            list.add(elements);
        }
        database.endTransaction();
        return (ArrayList<String>) list;
    }

    public String selectBills(int billId) throws SQLException{
        List<String> list = new ArrayList<>();
        String elements  = "";
        database.beginTransaction();
        String q = "Select b.Bill_Id, b.Client_Name, b.Client_Address, p.Part_Name, b.Bill_Quantity, b.Bill_Price, b.Part_Id  from Bills b " +
                "Inner Join Parts p ON " +
                "b.Part_Id = p.Part_Id " +
                "Where Bill_Id = " + billId;
        Cursor cursor = database.rawQuery(q, null);
        while(cursor.moveToNext()){
            int Bill_Id = cursor.getInt(cursor.getColumnIndex("Bill_Id"));
            String Client_Name = cursor.getString(cursor.getColumnIndex("Client_Name"));
            String Client_Address = cursor.getString(cursor.getColumnIndex("Client_Address"));
            String Part_Name = cursor.getString(cursor.getColumnIndex("Part_Name"));
            int Bill_Quantity = cursor.getInt(cursor.getColumnIndex("Bill_Quantity"));
            float Bill_Price = cursor.getFloat(cursor.getColumnIndex("Bill_Price"));
            int Part_Id = cursor.getInt(cursor.getColumnIndex("Part_Id"));
            elements = Bill_Id + "\t"
                    + Client_Name + "\t"
                    + Client_Address + "\t"
                    + Part_Name + "\t"
                    + Bill_Quantity + "\t"
                    + Bill_Price + "\t"
                    + Part_Id;
        }
        database.endTransaction();
        return elements;
    }

    public void insertParts(String category,String name, int quantity, float price) throws SQLException {
        String q = "Insert into Parts(Part_Category,Part_Name, Part_Quantity, Part_Price)" +
                "Values(?,?,?,?)";
        database.execSQL(q,new Object[]{category,name,quantity,price});
    }

    public void insertBills(String clientName,String clientAddress, int partId, int quantity, float price) throws SQLException {
        String q = "Insert into Bills(Client_Name, Client_Address, Part_Id, Bill_Quantity, Bill_Price)" +
                "Values(?,?,?,?,?)";
        database.execSQL(q,new Object[]{clientName,clientAddress, partId, quantity, price});
    }

    public void updatePart(int partId,String category, String partName, int quantity, float price) throws SQLException {
        String q = "Update Parts " +
                "Set " +
                "Part_Category = ?, " +
                "Part_Name = ? ," +
                "Part_Quantity = ? ," +
                "Part_Price = ? " +
                "Where Part_Id = ?";
        database.execSQL(q, new Object[]{category, partName, quantity, price, partId});
    }

    public void updateBill(int billId, String clientName, String clientAddress, int quantity, float price, int partId) throws SQLException {
        String q = "Update Bills " +
                "Set " +
                "Client_Name = ?, " +
                "Client_Address = ?, " +
                "Bill_Quantity = ?," +
                "Bill_Price = ?, " +
                "Part_Id = ? " +
                "Where Bill_Id = ?";

        database.execSQL(q, new Object[]{
                clientName,
                clientAddress,
                quantity,
                price,
                partId,
                billId
        });
    }

    public void deletePart(int partId) throws SQLException {
        String q = "Delete from Parts " +
                "Where Part_Id = " + partId;
        database.execSQL(q);
    }


    public void deleteBill(int billId) throws SQLException {
        String q = "Delete from Bills " +
                "Where Bill_Id = " + billId;
        database.execSQL(q);
    }
}
