package com.thesis.restaurantfinder;
import android.database.sqlite.SQLiteException;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "Restaurant.db";
	
	public static final String TABLE1_NAME = "restaurant_table";
	public static final String TABLE2_NAME = "branch_table";
	public static final String TABLE3_NAME = "visitlog_table";
	
	public static final String TABLE1_COL1 = "restaurant_id";
	public static final String TABLE1_COL2 = "restaurant_icon";
	public static final String TABLE1_COL3 = "restaurant_name";
	public static final String TABLE2_COL1 = "branch_id";
	public static final String TABLE2_COL2 = "restaurant_id";
	public static final String TABLE2_COL3 = "branch_address";
	public static final String TABLE2_COL4 = "branch_searchtags";
	public static final String TABLE3_COL1 = "visitlog_id";
	public static final String TABLE3_COL2 = "branch_id";
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE1_NAME + "(restaurant_id INTEGER PRIMARY KEY AUTOINCREMENT, restaurant_icon TEXT, restaurant_name TEXT)");
		db.execSQL("CREATE TABLE " + TABLE2_NAME + "(branch_id INTEGER PRIMARY KEY AUTOINCREMENT, restaurant_id INTEGER, branch_address TEXT, branch_searchtags TEXT)");
		db.execSQL("CREATE TABLE " + TABLE3_NAME + "(visitlog_id INTEGER PRIMARY KEY AUTOINCREMENT, branch_id INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
	}
	
	public void insertRestaurants(String icon, String name) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(TABLE1_COL2, icon);
		contentvalues.put(TABLE1_COL3, name);
		db.insert(TABLE1_NAME, null, contentvalues);
		db.close();
	}
	
	public void insertBranches(int restaurant_id, String branch_address, String searchtags) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(TABLE2_COL2, restaurant_id);
		contentvalues.put(TABLE2_COL3, branch_address);
		contentvalues.put(TABLE2_COL4, searchtags);
		db.insert(TABLE2_NAME, null, contentvalues);
		db.close();
	}
	
	public void insertLog(int branch_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(TABLE3_COL2, branch_id);
		db.insert(TABLE3_NAME, null, contentvalues);
		db.close();
	}
	
	public boolean checkRestaurantsExist() {
		String sql = "SELECT * FROM " + TABLE1_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor != null && cursor.getCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkBranchesExist() {
		String sql = "SELECT * FROM " + TABLE2_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor != null && cursor.getCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addRestaurants(Context context) {
	
    		Toast.makeText(context, "Adding restaurants...", Toast.LENGTH_LONG).show();
    		
    		insertRestaurants("mcdo", "McDonald's");
    		insertRestaurants("jollibee", "Jollibee");
    		insertRestaurants("kfc", "KFC");
    		insertRestaurants("chowking", "Chowking");
    		insertRestaurants("manginasal", "Mang Inasal");
    		
    		Toast.makeText(context, "Restaurants added...", Toast.LENGTH_SHORT).show();
    }
	
	public void addBranches(Context context) {
    	
    		Toast.makeText(context, "Adding restaurant branches...", Toast.LENGTH_LONG).show();
    		
    		insertBranches(1, "Morada Ave, Lipa City, Batangas", "mcdo lipa, mcdonalds lipa");
    		insertBranches(1, "C.M. Recto Cor. Esteban Mayo Sts, Lipa City, Batangas", "mcdo lipa, mcdonalds lipa");
    		insertBranches(1, "SM City Lipa, Brgy. Maraouy, Ayala Highway, Lipa City, Batangas", "mcdo lipa, mcdonalds lipa, mcdo sm, mcdonalds sm, mcdo sm lipa, mcdonalds sm lipa");
    		insertBranches(1, "Katipunan d., R., R. Soliman Street, Lipa City, Batangas", "mcdo lipa, mcdonalds lipa");
    		insertBranches(1, "President Jose P. Laurel Hwy, Lipa City, Batangas", "mcdo lipa, mcdonalds lipa");
    		insertBranches(1, "Marauoy, Lipa City, Batangas", "mcdo lipa, mcdonalds lipa");
    		insertBranches(1, "Lipa City, Batangas", "mcdo lipa, mcdonalds lipa");
    		insertBranches(1, "Waltermart, J.P Laurel National Highway, Tanauan City, Batangas", "mcdo tanauan, mcdonalds tanauan, mcdo waltermart, mcdonalds waltermart, mcdo waltermart tanauan, mcdonalds waltermart tanauan");
    		insertBranches(1, "A Mabini Avenue, Tanauan City, Batangas", "mcdo tanauan, mcdonalds tanauan");
    		insertBranches(1, "JP Laurel Highway, Tanauan City, Batangas", "mcdo tanauan, mcdonalds tanauan");
    		insertBranches(1, "Pan-Philippine Hwy, San Antonio, Sto. Tomas, Batangas", "mcdo santo tomas, mcdonalds santo tomas");
    		insertBranches(1, "SM Calamba, Real, Calamba, Laguna", "mcdo calamba, mcdonalds calamba, mcdo sm, mcdonalds sm, mcdo sm lipa, mcdonalds sm lipa");
    		insertBranches(1, "Real St. Hi-Way, Calamba, Laguna", "mcdo calamba, mcdonalds calamba");
    		insertBranches(1, "Walter Mart, Real Rd, Calamba, Laguna", "mcdo calamba, mcdonalds calamba, mcdo waltermart, mcdonalds waltermart, mcdo waltermart calamba, mcdonalds waltermart calamba");
    		insertBranches(1, "Manila S Rd Calamba, Laguna", "mcdo calamba, mcdonalds calamba");
    		insertBranches(1, "Pabalan St Calamba, Laguna", "mcdo calamba, mcdonalds calamba");
    		insertBranches(2, "C.M Recto Ave, Lipa City, Batangas", "jollibee lipa");
    		insertBranches(2, "Fiesta World Mall, JP Laurel St, Lipa City, Batangas", "jollibee lipa, jollibee fiesta mall");
    		insertBranches(2, "Pres. J.P Laurel Highway, Lipa City, Batangas", "jollibee lipa");
    		insertBranches(2, "Mary Mediatrix, Lipa City, Batangas", "jollibee lipa");
    		insertBranches(2, "SM City Lipa, Ayala National Highway, Sabang, Lipa City, Batangas", "jollibee lipa, jollibee sm, jollibee sm lipa");
    		insertBranches(2, "Waltermart Darasa, President Jose P. Laurel Hwy, Tanauan City, Batangas", "jollibee tanauan, jollibee waltermart, jollibee waltermart tanauan");
    		insertBranches(2, "J.P Laurel Cor. Mabini Avenue, Tanauan City, Batangas", "jollibee tanauan");
    		insertBranches(2, "A Mabini Avenue, Tanauan City, Batangas", "jollibee tanauan");
    		insertBranches(2, "Maharlika Hwy, Corner Gov. Malvar St., Sto. Tomas, Batangas", "jollibee santo tomas");
    		insertBranches(2, "Calamba Shopping Center, General Lim St., Calamba, Laguna", "jollibee calamba, jollibee calamba shopping center");
    		insertBranches(2, "Calamba, Laguna", "jollibee calamba");
    		insertBranches(2, "Walter Mart, Real Rd, Calamba, Laguna", "jollibee calamba, jollibee waltermart, jollibee waltermart calamba");
    		insertBranches(2, "SM Calamba, Real, Calamba, Laguna", "jollibee calamba, jollibee sm, jollibee sm calamba");
    		insertBranches(2, "Liana's Discount City, Manila S Rd, Calamba, Laguna", "jollibee calamba, jollibee lianas, jollibee lianas calamba");
    		insertBranches(2, "Jose Yulo Sr. Ave Cor. Silangan Industrial Park Road, Calamba, Laguna", "jollibee calamba");
    		insertBranches(2, "Letran, Halang, Calamba, Laguna", "jollibee calamba");
    		insertBranches(3, "Sm City Lipa, Ayala Highway, Lipa City, Batangas", "kfc lipa, kfc sm, kfc sm lipa");
    		insertBranches(3, "C.M Recto Avenue Corner T.M Kalaw, Poblacion, Lipa City, Batangas", "kfc lipa");
    		insertBranches(3, "JP Laurel, National Highway, MataasnaLupa, Lipa City, Batangas", "kfc lipa");
    		insertBranches(3, "Pres. Laurel Highway, Brgy 2, Tanauan City, Batangas", "kfc tanauan");
    		insertBranches(3, "Pan-Philippine Hwy, Sto. Tomas, Batangas", "kfc santo tomas");
    		insertBranches(3, "SM City Calamba, National Highway, Calamba, Laguna", "kfc lipa, kfc sm, kfc sm lipa");
    		insertBranches(4, "CM Recto Avenue, Lipa City, Batangas", "chowking lipa");
    		insertBranches(4, "J.P. Laurel Highway, Lipa City, Batangas", "chowking lipa");
    		insertBranches(4, "SM City Lipa, Ayala Highway, Lipa City, Batangas", "chowking lipa, chowking sm, chowking sm lipa");
    		insertBranches(4, "KM 79 Northbound Calabarzon Road, Petron Station, Barangay Tibig, Lipa City, Batangas", "chowking lipa, chowking calabarzon");
    		insertBranches(4, "MK Lina St, Lipa City, Batangas", "chowking lipa");
    		insertBranches(4, "Waltermart Center Tanauan, JP Laurel National Highway, Brgy. Darasa, Tanauan City, Batangas", "chowking tanauan, chowking waltermart, chowking waltermart tanauan");
    		insertBranches(4, "National Highway, Brgy. San Antonio, Sto. Tomas, Batangas", "chowking santo tomas");
    		insertBranches(4, "Liana's Mall, Parian, Calamba, Laguna", "chowking calamba, chowking lianas, chowking lianas calamba");
    		insertBranches(4, "National Highway, Checkpoint, Brgy. Paciano Rizal, Calamaba City, Laguna", "chowking calamba");
    		insertBranches(4, "SM Calamba, Real, Calamba, Laguna", "chowking calamba, chowking sm, chowking sm calamba");
    		insertBranches(5, "C.M. Recto Ave, Lipa City, Batangas", "mang inasal lipa");
    		insertBranches(5, "SM City Lipa, Brgy. Maraouy, Ayala Highway, Lipa City, Batangas", "mang inasal lipa, mang inasal sm, mang inasal sm lipa");
    		insertBranches(5, "Robinson's Place Lipa, Brgy. Maraouy, Ayala Highway, Lipa City, Batangas", "mang inasal lipa, mang inasal robinsons, mang inasal robinsons lipa");
    		insertBranches(5, "Big Ben Complex MataasnaLupa, Lipa City, Batangas", "mang inasal lipa, mang inasal big ben complex lipa");
    		insertBranches(5, "Waltermart Mall, President Jose P. Laurel Hwy, Barangay Darasa, Tanauan City, Batangas", "mang inasal tanauan, mang inasal waltermart, mang inasal waltermart tanauan");
    		insertBranches(5, "Lianas Junction Plaza, Maharlika Highway, Barangay Poblacion, Sto. Tomas, Batangas", "mang inasal santo tomas, mang inasal lianas, mang inasal lianas santo tomas");
    		insertBranches(5, "Sm City Calamba, National road, Barangay Real, Calamba, Laguna", "mang inasal calamba, mang inasal sm, mang inasal sm calamba");
    		insertBranches(5, "Waltermart, Calamba, Laguna", "mang inasal calamba, mang inasal waltermart, mang inasal waltermart calamba");
    		insertBranches(5, "iMall, Calamba, Laguna", "mang inasal calamba, mang inasal imall, mang inasal imall calamba");
    		insertBranches(5, "Puregold Calamba, National Highway, Brgy. Halang, Calamba, Laguna", "mang inasal calamba, mang inasal puregold, mang inasal puregold calamba");
    		
    		Toast.makeText(context, "Restaurant branches added...", Toast.LENGTH_SHORT).show();
    }
	
	public int countRestaurants() {
		String sql = "SELECT * FROM " + TABLE1_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		int count = 0;
		
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				count++;
			}
		}
		return count;
	}
	
	public int countBranches(int restaurant_id) {
		String sql = "SELECT * FROM " + TABLE2_NAME + " WHERE restaurant_id = " + restaurant_id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		int count = 0;
		
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				count++;
			}
		}
		return count;
	}
	
	public int countBranchVisit(int branch_id) {		
		String sql = "SELECT COUNT(branch_id) FROM " + TABLE3_NAME + " WHERE branch_id = " + branch_id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		int count = 0;
		
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
		}
		return count;
	}
	
	public Cursor getAllRestaurants() {
		String sql = "SELECT * FROM " + TABLE1_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		return cursor;
	}
	
	public String getRestaurantName(int restaurant_id) {
		String restaurant_name = "";
		String sql = "SELECT restaurant_name FROM " + TABLE1_NAME + " WHERE " + TABLE1_COL1 + " = " + restaurant_id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor != null && cursor.getCount() == 1) {
			while (cursor.moveToNext()) {
				restaurant_name = cursor.getString(0);
			}
		}
		return restaurant_name;
	}
	
	public String getRestaurantNamebyBranchID(int branch_id) {
		String restaurant_name = "";
		String sql = "SELECT restaurant_table.restaurant_name FROM restaurant_table INNER JOIN branch_table ON restaurant_table.restaurant_id = branch_table.restaurant_id WHERE branch_table.branch_id = " + branch_id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor != null && cursor.getCount() == 1) {
			while (cursor.moveToNext()) {
				restaurant_name = cursor.getString(0);
			}
		}
		return restaurant_name;
	}
	
	public Cursor getAllBranches(int restaurant_id) {
		String sql = "SELECT branch_table.branch_id, branch_table.branch_address FROM branch_table INNER JOIN restaurant_table ON branch_table.restaurant_id = restaurant_table.restaurant_id WHERE branch_table.restaurant_id = " + restaurant_id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		return cursor;
	}
	
	public Cursor getSearchResults(String search_key) {
		Cursor cursor = null;
		try {
			String sql = "SELECT restaurant_table.restaurant_icon, restaurant_table.restaurant_name, branch_table.branch_id, branch_table.branch_address, branch_table.branch_searchtags FROM branch_table INNER JOIN restaurant_table ON branch_table.restaurant_id = restaurant_table.restaurant_id WHERE restaurant_table.restaurant_name || ' ' || branch_table.branch_address LIKE '%" + search_key + "%' OR branch_table.branch_searchtags LIKE '%" + search_key + "%'";
			SQLiteDatabase db = this.getReadableDatabase();
			cursor = db.rawQuery(sql, null);
		}
		catch (SQLiteException e) {
			cursor.close();
		}
		return cursor;
	}
	
	public int countSearchResults(String search_key) {
		Cursor cursor = null;
		int count = 0;

		try {
			String sql = "SELECT restaurant_table.restaurant_icon, restaurant_table.restaurant_name, branch_table.branch_id, branch_table.branch_address, branch_table.branch_searchtags FROM branch_table INNER JOIN restaurant_table ON branch_table.restaurant_id = restaurant_table.restaurant_id WHERE restaurant_table.restaurant_name || ' ' || branch_table.branch_address LIKE '%" + search_key + "%' OR branch_table.branch_searchtags LIKE '%" + search_key + "%'";
			SQLiteDatabase db = this.getReadableDatabase();
			cursor = db.rawQuery(sql, null);
			
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					count++;
				}
			}
		}
		catch (SQLiteException e) {
			cursor.close();
		}
		return count;
	}
	
}
