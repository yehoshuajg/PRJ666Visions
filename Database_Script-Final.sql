drop database if exists StoreDB;
create database StoreDB;
use StoreDB;

/*-------------------------- CATEGORY -----------------------------*/
create table Category
(
	ID				int				not null AUTO_INCREMENT,
	`Name`			varchar(35)		not null,
	Description		varchar(60),
	ParentID		int,

	PRIMARY KEY(ID),
	Unique(`Name`),
	FOREIGN KEY (ParentID) REFERENCES Category(ID) 
);

/*-------------------------- SUPPLIER -----------------------------*/
create table Supplier
(
	ID				int				not null	AUTO_INCREMENT,
	`Name`			varchar(35)		not null,
	Street			varchar(40)		not null,
	City			varchar(15)		not null,
	State_Province	varchar(20)		not null,
	PostalCode		varchar(10)		not null,
	PhoneNumber		varchar(15)		not null,
	Email			varchar(50),
	`Status`		varchar(9)		not null DEFAULT 'Active',
    MinimumOrderCost	double		not null,
	DeliveryCost		double		DEFAULT 0,
	
	PRIMARY KEY(ID),
	CONSTRAINT CK_Supplier_PhoneNumber check (PhoneNumber like '[0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_Supplier_MinimumOrderCost check (MinimumOrderCost > 150),
    CONSTRAINT CK_Supplier_Status check (`Status` = "Active" OR `Status` = "In-Active" OR `Status` = "InActive"),
	Unique(`Name`, Street, PhoneNumber)
);

/*-------------------------- CONTACT -----------------------------*/
create table Contact
(
	ID				int				not null	AUTO_INCREMENT,
	FirstName		varchar(15)		not null,
	LastName		varchar(15)		not null,
	PhoneNumber		varchar(15)		not null,
	Email			varchar(50),
    Department		varchar(20)		not null,
	SupplierID		int				not null,

	PRIMARY KEY(ID),
	FOREIGN KEY (SupplierID) REFERENCES Supplier(ID),
	CONSTRAINT CK_Contact_PhoneNumber check (PhoneNumber like '[0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'),
	Unique(PhoneNumber)
);

/*-------------------------- PRODUCTS/INVENTORY -----------------------------*/
create table Product
(
	ID				int(8)				not null,
	`Name`			varchar(50)			not null,	
	Description		varchar(80),
	CategoryID		int					not null,
	SubCategoryID	int,			
	SalePrice		double				not null,
	Quantity		int					DEFAULT 0,	
	Notes			varchar(80),
	
	PRIMARY KEY(ID),
	FOREIGN KEY (CategoryID) REFERENCES Category(ID),
	FOREIGN KEY (SubCategoryID) REFERENCES Category(ID),
	CONSTRAINT CK_Product_Categories check(CategoryID != SubCategoryID),
	CONSTRAINT CK_Product_SalePrice check(SalePrice > 0)
);

create table Product_Supplier
(
	ProductID		int(8)		not null, 
	SupplierID		int			not null,
	UnitCost		double		not null,
	Quantity		int			DEFAULT 0,
	
	PRIMARY KEY(ProductID, SupplierID),
	FOREIGN KEY (ProductID) REFERENCES Product(ID),
	FOREIGN KEY (SupplierID) REFERENCES Supplier(ID),
    CONSTRAINT CK_Product_UnitCost check(UnitCost > 0),
    CONSTRAINT CK_Product_Quantity check(Quantity >= 0)
);

/*-------------------------- PRICE_HISTORY -----------------------------*/
create table PriceHistory
(
	EffectiveDate	timestamp		DEFAULT current_timestamp,
	ProductID		int(8)			not null,
	NewPrice		double			not null,
    Reason			varchar(100),
	
	PRIMARY KEY(EffectiveDate, ProductID),
	CONSTRAINT CK_PriceHistory_NewPrice check(NewPrice > 0)
);


/*-------------------------- QUALITY_ADJUSTMENT -----------------------------*/
create table QAdjustment
(
	ID			int				not null	AUTO_INCREMENT,
	ProductID	int(8)			not null,
	Quantity	int 			not null,
	Reason		varchar(100),
	ChangeDate	DATE			not null,
	
	PRIMARY KEY(ID),
	FOREIGN KEY (ProductID) REFERENCES Product(ID),
	CONSTRAINT CK_QAdjustment_Quantity check(Quantity > 0)
);

/*-------------------------- PROMOTION -----------------------------*/
create table Promotion
(
	ID					int				not null	AUTO_INCREMENT,
	DiscountValue		double,
	DiscountPercent		float,
	
	PRIMARY KEY(ID),
	CONSTRAINT CK_Promotion_Values check((DiscountValue > 0) and (DiscountPercent = 0) or 
										(DiscountValue = 0 and DiscountPercent > 0))
);

/*-------------------------- POSITIONS -----------------------------*/
create table Positions
(
	ID				int				not null AUTO_INCREMENT,
	`Name`			varchar(20)		not null,
	Description		varchar(50),
	
	PRIMARY KEY(ID)
);

/*-------------------------- EMPLOYEE -----------------------------*/
create table Employee
(
	ID				int				not null	AUTO_INCREMENT,
	FirstName		varchar(15)		not null,
	LastName		varchar(15)		not null,
	Street			varchar(40),
	City			varchar(15),
	State_Province	varchar(20),
	PostalCode		varchar(10),
	HomePhone		varchar(15),
	CellPhone		varchar(15),
	Email			varchar(50),
	PositionID		int				not null,
	JobType			varchar(12)		not null,
	UserName		varchar(15)		not null,
	`Password`		varchar(200)		not null,
	HireDate		DATE			not null,
	TerminationDate	DATE,
	
	PRIMARY KEY(ID),
	FOREIGN KEY (PositionID) REFERENCES Positions(ID),
	Unique(UserName),
	CONSTRAINT CK_Employee_HomePhoneNumber check (HomePhoneNumber like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	CONSTRAINT CK_Employee_CellPhoneNumber check (CellPhoneNumber like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	CONSTRAINT CK_Employee_ContectNumbers check (HomePhoneNumber != CellPhoneNumber)
);

/*-------------------------- TRANSACTION -----------------------------*/
create table `Transaction`
(
	ID				int				not null	AUTO_INCREMENT,
	CreateDate		DATETIME		not null,
	SubTotal		double			not null,
	Tax				double			not null,
	Total			double			not null,
	TransactionType	varchar(15),
	Method			varchar(15)		DEFAULT 'CASH',
	PromotionID		int,
	EmployeeID		int 			not null,

	PRIMARY KEY(ID),
	FOREIGN KEY (PromotionID) REFERENCES Promotion(ID),
	FOREIGN KEY (EmployeeID) REFERENCES Employee(ID),
	CONSTRAINT CK_Transaction_SubTotal check(SubTotal > 0),
	CONSTRAINT CK_Transaction_Tax check(Tax > 0),
	CONSTRAINT CK_Transaction_Total check(Total > 0)
);

/*-------------------------- TRANSACTION_RECORD -----------------------------*/
create table TransactionRecord
(
	TransactionID	int				not null,
	ProductID		int(8)			not null,
	QuantitySold	int 			not null,
	UnitPrice		decimal(6, 2)	not null,
    Returned		boolean			DEFAULT False,
    DateReturned	Date,
    EmployeeID		int,
	
	PRIMARY KEY(TransactionID, ProductID),
	FOREIGN KEY (TransactionID) REFERENCES Transaction(ID),
	FOREIGN KEY (ProductID) REFERENCES Product(ID),
    FOREIGN KEY (EmployeeID) REFERENCES Employee(ID),
	CONSTRAINT CK_TransactionRecord_Quantity check (QuantitySold > 0),
	CONSTRAINT CK_TransactionRecord_UnitPrice check(UnitPrice > 0)
);

/*-------------------------- ORDER AND PAYMENT RELATED CONTENT -----------------------------*/
create table Invoice
(
	ID				int				not null	AUTO_INCREMENT,
	AmountDue		double						DEFAULT 0,
	AmountPaid		double						DEFAULT 0,
	ReceivedDate	timestamp		DEFAULT current_timestamp,
	SupplierID		int 			not null,
    Sup_InvoiceID	int 			not null	Default 0,
	
	PRIMARY KEY(ID),
	FOREIGN KEY (SupplierID) REFERENCES Supplier(ID),
	CONSTRAINT CK_Invoice_AmountDue check (AmountDue > 0),
	CONSTRAINT CK_Invoice_AmountPaid check (AmountPaid > 0)	
);

create table `Order`
(
	ID				int				not null	AUTO_INCREMENT,
	SupplierID		int				not null,
	EmployeeID		int				not null,
	CreateDate		DATETIME		not null,
	ReceivedDate	DATETIME,
	Cost			double			not null,
	AmountPaid		double,
	InvoiceID		int,
	
	PRIMARY KEY(ID),
	FOREIGN KEY (SupplierID) REFERENCES Supplier(ID),
	FOREIGN KEY (EmployeeID) REFERENCES Employee(ID),
	FOREIGN KEY (InvoiceID) REFERENCES Invoice(ID),
	CONSTRAINT CK_Order_Cost check (Cost > 0),
	CONSTRAINT CK_Order_AmountPaid check (AmountPaid > 0 and AmountPaid <= Cost)
);

/*-------------------------- ORDER_DETAIL -----------------------------*/
create table OrderDetail
(

	OrderID				int			not null,
	ProductID			int(8) 		not null,
	OrderedQuantity		int 		not null,
	ReceivedQuantity	int			DEFAULT 0,
	Cost				double		DEFAULT 0,
	
	PRIMARY KEY(OrderID, ProductID),
	FOREIGN KEY (OrderID) REFERENCES `Order`(ID),
	FOREIGN KEY (ProductID) REFERENCES Product(ID),
	CONSTRAINT CK_OrderDetail_Cost check (Cost > 0)
);

/*---------------------------------------------------------------------------------------------------------------------------------------------*/
/*---------------------------------------------------INSERTING TEST DATA-----------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------------------------------------------------------------*/

/*-------------------------- CATEGORY TABLE-----------------------------*/
INSERT INTO Category (ID, `Name`) VALUES (1, "Clothing");
INSERT INTO Category (ID, `Name`, ParentID) VALUES (2, "Womenswear", 1);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (3, "Menswear", 1);
INSERT INTO Category (ID, `Name`) VALUES (4, "Personal Care");
INSERT INTO Category (ID, `Name`, ParentID) VALUES (5, "Skin Care", 4);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (6, "Hair Care", 4);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (7, "Cosmetics", 4);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (8, "Deodorants and Anti Perspirants", 4);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (9, "Soap, Bath and Shower Products", 4);
INSERT INTO Category (ID, `Name`) VALUES (10, "Household");
INSERT INTO Category (ID, `Name`, ParentID) VALUES (11, "Air Fresheners", 10);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (12, "Furniture", 10);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (13, "Household Cleaners", 10);
INSERT INTO Category (ID, `Name`) VALUES (14, "Consumer Electronics");
INSERT INTO Category (ID, `Name`, ParentID) VALUES (15, "Audio Equipment", 14);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (16, "Cameras and Camera Equipment", 14);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (17, "Lighting Equipment", 14);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (18, "Computer Accessories", 14);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (19, "Car Electronics and GPS", 14);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (20, "Video Games and Consoles", 14);
INSERT INTO Category (ID, `Name`) VALUES (21, "Stationery and Greeting Cards");
INSERT INTO Category (ID, `Name`) VALUES (22, "Jewelry and Watches");
INSERT INTO Category (ID, `Name`) VALUES (23, "Food");
INSERT INTO Category (ID, `Name`, ParentID) VALUES (24, "Snacks and Confectionery", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (25, "Health Food and Sports Nutrition", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (26, "Canned Food", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (27, "Frozen Food", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (28, "Meat, Poultry and Eggs", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (29, "Fruit and Vegetables", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (30, "Baked Goods", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (31, "Cereals", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (32, "Soup", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (33, "Rice and Rice Products", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (34, "Nuts, Seeds and Dried Fruit", 23);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (35, "Sugar and Sweeteners", 23);
INSERT INTO Category (ID, `Name`) VALUES (36, "Beverage");
INSERT INTO Category (ID, `Name`, ParentID) VALUES (37, "Soft Drinks", 36);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (38, "Hot Drinks", 36);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (39, "Bottled Water", 36);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (40, "Sport, Energy and Functional Drinks", 36);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (41, "Juice", 36);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (42, "Alcoholic Beverages", 36);
INSERT INTO Category (ID, `Name`) VALUES (43, "Tobacco");
INSERT INTO Category (ID, `Name`, ParentID) VALUES (44, "Cigarettes", 43);
INSERT INTO Category (ID, `Name`) VALUES (45, "Dairy Products");
INSERT INTO Category (ID, `Name`, ParentID) VALUES (46, "Milk and Cream", 45);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (47, "Yogurt", 45);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (48, "Cheese", 45);
INSERT INTO Category (ID, `Name`, ParentID) VALUES (49, "Butter", 45);

/*-------------------------- SUPPLIER -----------------------------*/
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (1,"Et PC","P.O. Box 790, 297 Adipiscing Road","Melton Mowbray","LE","98530","1-824-231-7933","et.libero.Proin@doloregestasrhoncus.com",450);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (2,"Et Institute","P.O. Box 873, 6994 Donec Ave","Hamilton","NI","357844","1-620-884-606","ac@semmollisdui.ca",200);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (3,"Tellus LLP","516-6342 At Ave","Wanaka","South Island","61305","1-674-427-6341","ipsum.Suspendisse@Nunc.edu",400);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (4,"Odio Institute","331-9577 Accumsan Ave","Matamata","NI","07118","1-718-798-0048","Curabitur@nulla.ca",390);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (5,"Velit Aliquam Consulting","Ap #924-8262 Nonummy Rd.","Istanbul","Istanbul","97502","1-911-459-0148","magna.Phasellus.dolor@malesuada.org",240);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (6,"Metus Company","7374 Consectetuer Road","Kingussie","Inverness-shire","85277","1-554-196-3620","non.quam@conguea.com",177);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (7,"Et Ultrices Company","1073 Non Avenue","Motueka","South Island","5061","1-481-861-5492","est@nuncnullavulputate.edu",464);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (8,"Eget Varius Ultrices LLP","1714 A Rd.","Cañas","G","72-844","1-676-371-9467","penatibus.et.magnis@nisimagnased.net",341);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (9,"Egestas Urna Justo Associates","410-2391 Varius. Ave","Port Augusta","South Australia","01355","1-250-644-5093","congue@anteVivamusnon.net",367);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (10,"Etiam Imperdiet Incorporated","818-3805 Semper Street","Wałbrzych","Dolnośląskie","43320-331","1-511-785-7444","lectus@magnaseddui.net",492);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (11,"Cras Eu Tellus LLP","253-9944 Lacus. Rd.","Brecon","BR","90-975","1-205-524-5003","eget.massa.Suspendisse@risusIn.com",467);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (12,"Eu Nulla LLC","5457 Interdum Avenue","Ciplet","Luik","K1E 3Y2","1-323-969-8097","sem.Nulla@vitaevelitegestas.ca",289);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (13,"Nunc Quis Ltd","Ap #671-3809 Ligula Street","Aserrí","SJ","57383","1-407-971-8500","arcu@euismod.co.uk",309);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (14,"Dolor Elit Pellentesque Associates","Ap #643-8558 Sed Av.","Gary","IN","7865","1-387-167-3362","placerat.eget@idenim.edu",448);
INSERT INTO Supplier (ID,`Name`,Street,City,State_Province,PostalCode,PhoneNumber,Email,MinimumOrderCost) VALUES (15,"Malesuada Fames Ac Institute","5052 Nec, Ave","Bama","Borno","12513","1-937-322-2195","risus.Nulla@rutrumnon.org",408);

update storedb.supplier
set DeliveryCost = ROUND((MinimumOrderCost * 0.2));

/*-------------------------- CONTACT -----------------------------*/
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Janice', 'Ferguson', '2-173-026-7699', null, 'Internal Auditor', 15);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Billy', 'Morrison', '6-462-527-3077', null, 'Internal Auditor', 5);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Emily', 'Ford', '4-811-090-0090', 'eford2@geocities.jp', 'Pharmacist', 12);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Sara', 'Lopez', '9-706-218-9941', 'slopez3@chicagotribune.com', 'Statistician III', 16);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Chris', 'Bennett', '2-278-404-8771', 'cbennett4@thetimes.co.uk', 'Marketing Manager', 16);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Kelly', 'Barnes', '8-523-878-4344', 'kbarnes5@baidu.com', 'Product Engineer', 6);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Diana', 'Stanley', '3-080-047-1672', 'dstanley6@si.edu', 'Automation Specialist III', 3);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Gerald', 'Johnston', '8-273-279-0912', null, 'Financial Advisor', 9);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Deborah', 'Freeman', '4-370-595-4322', 'dfreeman8@comcast.net', 'Assistant Manager', 4);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Roger', 'Hansen', '3-849-034-5404', null, 'Senior Financial Analyst', 15);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Louise', 'Smith', '6-670-315-2868', 'lsmitha@typepad.com', 'Tax Accountant', 5);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Ralph', 'Carpenter', '6-778-810-3753', null, 'Sales Representative', 11);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Sharon', 'Freeman', '1-812-295-1134', 'sfreemanc@freewebs.com', 'Human Resources Manager', 2);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Kenneth', 'Barnes', '7-935-009-2334', 'kbarnesd@acquirethisname.com', 'VP Sales', 14);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Kathleen', 'Hawkins', '7-272-624-5684', 'khawkinse@blinklist.com', 'Analyst Programmer', 15);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Brian', 'Porter', '1-637-923-6231', 'bporterf@google.com', 'Senior Financial Analyst', 4);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Bruce', 'Carroll', '0-217-417-6182', null, 'Recruiter', 8);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Terry', 'Hunt', '1-853-529-0258', 'thunth@amazon.co.jp', 'Human Resources Assistant I', 15);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Dennis', 'Roberts', '0-313-252-0730', 'drobertsi@trellian.com', 'Registered Nurse', 8);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Stephen', 'Hawkins', '4-467-322-6689', 'shawkinsj@rakuten.co.jp', 'Food Chemist', 10);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Jesse', 'Stephens', '4-578-312-6573', 'jstephensk@eepurl.com', 'Accounting Assistant IV', 4);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Antonio', 'Anderson', '4-540-878-1253', 'aandersonl@huffingtonpost.com', 'Research Associate', 5);
insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID) values ('Joe', 'Rodriguez', '1-461-700-0906', null, 'Financial Analyst', 11);

/*-------------------------- PRODUCTS/INVENTORY -----------------------------*/
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000001, 'Pollens - Trees, Ash, White Fraxinus americana', 'Jubilant HollisterStier LLC', 18, 19, 248.13, 'Path fx tibia fibula');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000002, 'Cellex-C Sun Shade', 'Cellex-C International Inc', 22, 40, 144.45, 'Acc poisn-cns depres NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000003, 'Prevacid', 'Bryant Ranch Prepack', 10, 26, 91.6, 'Jt derangment NEC-shlder');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000004, 'GUNA-ADDICT 2', 'Guna spa', 42, 23, 76.83, 'NB intraven hem NOS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000005, 'Tamiflu', 'Dispensing Solutions, Inc.', 40, 10, 92.02, 'Induced malaria');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000006, 'DR.G DEWRINKLE BOOSTER AMPLE', 'GOWOONSESANG COSMETICS CO., LTD.', 20, 11, 218.93, 'Amniocentesis affect NB');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000007, 'Diphenhydramine HCL', 'SDA Laboratories, Inc.', 6, 49, 135.13, 'Acc cut/hem in surgery');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000008, 'Stay Perfect Foundation Sunscreen', 'Boots Retail USA Inc', 14, 46, 244.14, 'Hourglass stricture stom');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000009, 'Gabapentin', 'Cardinal Health', 33, 49, 175.52, 'Herpangina');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000010, 'ISOVUE', 'BRACCO DIAGNOSTICS INC', 42, 1, 269.41, 'Poison-capillary act agt');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000011, 'ADSOL Red Cell Preservation Solution System', 'Fenwal, Inc.', 49, 23, 251.75, 'Fx arm mult/NOS-closed');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000012, 'Radiogardase', 'Heyl Chem.-pharm. Fabrik GmbH & Co. KG', 32, 39, 113.59, 'Liver laceration, minor');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000013, 'Contac Cold and Flu', 'Meda Consumer Healthcare Inc.', 21, 41, 75.49, 'Acc-powered lawn mower');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000014, 'MEDROXYPROGESTERONE ACETATE', 'Physicians Total Care, Inc.', 28, 47, 114.69, 'Sprain interphalangeal');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000015, 'clindamycin phosphate', 'Physicians Total Care, Inc.', 25, 24, 104.49, 'Retina layer separat NOS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000016, 'Adult Low Strength Aspirin', 'Ilex Consumer Products Group, LLC', 19, 38, 97.28, 'Late ef CV dis-cognf def');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000017, 'Perphenazine', 'Qualitest Pharmaceuticals', 31, 24, 94.2, 'Walking,marching,hiking');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000018, 'Cetirizine Hydrochloride', 'Pharmaceutical Associates, Inc.', 47, 41, 90.75, 'TB urinary NEC-cult dx');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000019, 'Meprobamate', 'Watson Laboratories, Inc.', 45, 9, 263.04, 'Opn wnd hip/thigh w tend');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000020, 'Depo-Medrol', 'Pharmacia and Upjohn Company', 39, 32, 162.29, 'TB of adrenal-no exam');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000021, 'Hydrochlorothiazide', 'Contract Pharmacy Services-PA', 42, 13, 57.68, 'Intestinal TB NEC-unspec');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000022, 'Baclofen', 'REMEDYREPACK INC.', 34, 29, 31.81, 'Wrist drop');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000023, 'Drosera comp.', 'Uriel Pharmacy Inc.', 12, 47, 105.38, 'Passive-aggressiv person');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000024, 'CHRIOLOGY Shiatsu Magic Point', 'Chrio Korea Co., Ltd', 18, 4, 257.13, 'Fx fibula NOS-open');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000025, 'Bryophyllum e fol. 10', 'Uriel Pharmacy Inc.', 24, 24, 103.04, 'Monoc exotrop w v pattrn');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000026, 'SOFTSOAP', 'Colgate-Palmolive Company', 26, 48, 108.27, 'Oth spcf dsdr urethra');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000027, 'Oxygen', 'Machine & Welding Supply Company', 37, 38, 229.23,  '3rd brn w loss-hand NOS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000028, 'Zep Professional Healthcare Worker', 'Zep Inc.', 38, 9, 203.38, 'Renal hyperten-del p/p');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000029, 'Eszopiclone', 'Dr. Reddy''s Laboratories Limited', 9, 36, 74.92, 'Burn NOS head-unspec');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000030, 'SPOT REMOVER ACNE TREATMENT PADS', 'ORIGINS NATURAL RESOURCES INC.', 45, 46, 299.55, 'Ca in situ skin ear');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000031, 'Arnica Aurum 20/30', 'Uriel Pharmacy Inc.', 24, 22, 47.52, 'Post dislocation of lens');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000032, 'Chlorpheniramine Maleate', 'Rugby Laboratories Inc.', 35, 9, 151.34, 'Post-trauma headache NOS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000033, 'DELFLEX', 'Fresenius Medical Care North America', 3, 38, 219.45, 'Uter dystocia NOS-unspec');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000034, 'DEXTROMETHORPHAN HYDROBROMIDE', 'SPIRIT PHARMACEUTICALS,LLC', 40, 48, 93.94, 'Alt esotropia w v pattrn');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000035, 'LAMICTAL', 'GlaxoSmithKline LLC', 3, 30, 63.72, 'Crushing injury NOS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000036, 'medroxyprogesterone acetate', 'American Health Packaging', 27, 19, 102.46, 'TB sp crd abscess-unspec');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000037, 'Benazepril Hydrochloride', 'Amneal Pharmaceuticals', 31, 26, 240.85, 'Malignant neopl jejunum');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000038, 'Common Sagebrush', 'Nelco Laboratories, Inc.', 18, 32, 136.98, 'Oth curr cond-delivered');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000039, 'Histamine', 'BioActive Nutritional, Inc.', 3, 18, 243.6, 'Fam hx MEN syndrome');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000040, 'Cephalexin', 'Blenheim Pharmacal, Inc.', 28, 46, 58.56, 'Arizona enteritis');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000041, 'Botanics Daily Hand Therapy Sunscreen SPF 10', 'Boots Retail USA Inc', 19, 1, 60.39, 'Local salmonella inf NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000042, 'Robitussin Peak Cold', 'Richmond Division of Wyeth', 13, 21, 214.24, 'Lymphangitis');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000043, 'ShopKo Absolute Daily Moisturizing Broad Spectrum', 'Shopko Stores Operating Co., LLC', 34, 35, 224.5, 'Progressive iris atrophy');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000044, 'Lortab', 'Cardinal Health', 13, 40, 155.17, 'Ftl cmp in utro-del-p/p');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000045, 'Supremacie', 'Ventura International LTD', 39, 29, 261.06, 'Abdmnal pain lft up quad');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000046, 'Advanced Antiseptic', 'Demoulas Super Markets, Inc', 12, 47, 43.39, '3rd deg burn face NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000047, 'Antiperspirant Cedarwood', 'Every man jack', 49, 11, 219.68, 'Follow-up exam NOS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000048, 'Gabapentin', 'MedVantx, Inc.', 40, 3, 165.86, 'Sports,athletics NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000049, 'Citrus and Olive Antibacterial Foaming Hand Wash', 'SJ Creations, Inc.', 10, 4, 64.63, 'Superficial injury NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000050, 'Stool Softener', 'TOP CARE (Topco Associates LLC)', 24, 47, 169.78, 'Chr iridocyclitis NOS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000051, 'Visine', 'Johnson & Johnson Healthcare Products, Division of McNEIL-PPC, Inc.', 40, 32, 196.13, 'Retina microaneurysm NOS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000052, 'Bac Stop', 'Wayne Concept Manufacturing Co Inc', 28, 18, 264.16, '1st deg burn upper arm');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000053, 'Up and Up Nicotine', 'Target Corporation', 16, 2, 110.56, 'Sprain calcaneofibular');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000054, 'Granisetron Hydrochloride', 'Roxane Laboratories, Inc', 49, 40, 270.34, 'Perinatal jaundice NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000055, 'Business Elite Amenity', 'Hangzhou Zhuwen Inflight & Travel Products Co., Ltd.', 21, 23, 83.74, 'Abort NOS w hemorr-inc');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000056, 'YOUTH SURGE', 'CLINIQUE LABORATORIES INC.', 29, 41, 133.53, 'Ben carcinoid duodenum');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000057, 'rexall ibuprofen', 'Dolgencorp, LLC', 26, 43, 128.35, 'Fx corpus cavrnosm penis');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000058, 'Mirtazapine', 'REMEDYREPACK INC.', 1, 14, 142.82, 'Oth placent cond-deliver');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000059, 'Contact Allergy', 'Apotheca Company', 11, 11, 85.14, 'Family hx-skin condition');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000060, 'Prednisone', 'REMEDYREPACK INC.', 13, 37, 62.03, 'Papilledema w decr press');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000061, 'EAD', 'European Perfume Works Co. L.L.C', 36, 12, 276.48, 'Hyperem w metab dis-del');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000062, 'Molds, Rusts and Smuts, Stemphylium botryosum', 'Jubilant HollisterStier LLC', 12, 24, 95.22, 'Dependent personality');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000063, 'milk of magnesia', 'CVS Pharmacy', 21, 18, 218.11, 'Staphylcocc septicem NOS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000064, 'Metformin Hydrochloride', 'Aphena Pharma Solutions - Tennessee, LLC', 11, 10, 247.9, 'Pap smear vagina w ASC-H');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000065, 'MBM 2 Gallbladder', 'The Wellness Center', 3, 6, 205.77, 'Subdural hem w opn wound');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000066, 'LAZANDA', 'Archimedes Pharma US Inc.', 20, 10, 134.09, 'Mv traff acc NEC-driver');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000067, 'ZYPREXA', 'STAT RX USA LLC', 5, 7, 168.35, 'Burn NOS multiple site');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000068, 'SEEQUIN 2', 'Vivier Pharma, Inc.', 30, 8, 59.48, 'Fall striking sharp obj');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000069, 'Somnitab', 'FRED''S, INC.', 14, 15, 163.26, 'Reentrant coll-ped cycl');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000070, 'LMX4', 'Ferndale Laboratories, Inc.', 39, 42, 165.97, 'Avulsion of eye');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000071, 'Fluticasone Propionate', 'Global Pharmaceuticals, Division of IMPAX Laboratories Inc.', 49, 2, 201.91, 'Iliac artery aneurysm');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000072, 'hydroxyzine pamoate', 'NCS HealthCare of KY, Inc dba Vangard Labs', 4, 19, 247.36, 'Personl hx infection CNS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000073, 'Gabapentin', 'Breckenridge Pharmaceutical, Inc.', 20, 5, 182.06, 'Corneal dsdr contct lens');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000074, 'Effexor', 'Physicians Total Care, Inc.', 20, 10, 272.1, 'Surg compl-body syst NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000075, 'Diphenhydramine Hydrochloride', 'Hospira, Inc.', 49, 29, 264.63, 'BMI 26.0-26.9,adult');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000076, 'ROBINIA PSEUDOACACIA POLLEN', 'ALK-Abello, Inc.', 5, 6, 66.17, 'Acq neck deformity');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000077, 'Piroxicam', 'Golden State Medical Supply, Inc.', 1, 33, 56.78, 'Voice/resonance dis NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000078, 'Neutrogena Complete Acne Therapy Solution', 'Neutrogena Corporation', 37, 1, 35.7,  'Subarach hem-coma NOS');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000079, 'hydrocortisone', 'Preferred Pharmaceuticals, Inc.', 22, 47, 152.64, 'Fx distal radius NEC-cl');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000080, 'Evtox', 'BioActive Nutritional, Inc.', 48, 18, 176.88, 'Syphilitic encephalitis');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000081, 'Vitamin A and D', 'H and P Industries, Inc. dba Triad Group', 28, 11, 199.59, 'Mv traff acc-pers NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000082, 'ketamine hydrochloride', 'JHP Pharmaceuticals, LLC', 3, 48, 144.82, 'Pulmon TB NOS-histo dx');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000083, 'AMITRIPTYLINE HYDROCHLORIDE', 'Direct Rx', 44, 44, 211.15, 'Gambling and betting');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000084, 'nighttime cold and flu relief', 'Walgreen Company', 8, 31, 235.52, 'Cerebrosp fluid otorrhea');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000085, 'Fusarium compactum', 'Nelco Laboratories, Inc.', 26, 5, 98.22, 'Rickettsioses NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000086, 'Dove Ultimate Sensitive Care Fragrance Free', 'Conopco Inc. d/b/a Unilever', 25, 2, 129.33, 'Brain hem NEC-brief coma');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000087, 'Pro-Den Rx', 'Zila Therapeutics, Inc.', 43, 20, 46.86, 'Toxoplasm chorioretinit');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000088, 'Zolpidem Tartrate', 'REMEDYREPACK INC.', 6, 13, 235.8, 'Longitud defic phalanges');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000089, 'Bio Willow', 'Apotheca Company', 1, 28, 131.11, 'Fit contact lens/glasses');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000090, 'Galentic Vitamin A D', 'Galentic Pharma (India) Priva Te Limited', 22, 33, 46.53, 'Cerebrovasc disease NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000091, 'Sulwhasoo Lumitouch', 'AMOREPACIFIC CO.', 43, 11, 270.54, 'Hemorrhage from throat');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000092, 'FOSINOPRIL Na', 'Apotex Corp.', 25, 9, 238.15, 'NB acidosis NEC');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000093, 'IRIS', 'AMERIFOODS TRADING COMPANY', 34, 41, 176.29, 'Cl skull vlt fx-mod coma');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000094, 'Nutricel Additive System - CP2D', 'Medsep Corporation', 36, 4, 294.8, 'Prader-willi syndrome');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000095, 'Collagen', 'VIATREXX BIO INCORPORATED', 5, 34, 104.96, 'Oth acq limb deformity');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000096, 'Sunmark Nicotine', 'McKesson', 13, 49, 152.04, 'Schizo NOS-subchr/exacer');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000097, 'Metoclopramide', 'American Health Packaging', 20, 38, 126.32, 'Bact arthritis-hand');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000098, 'Pollens - Rough Redroot Amaranthus retroflexus', 'Jubilant HollisterStier LLC', 2, 22, 230.88, 'Open wnd knee/leg/ankle');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000099, 'Ionosol and Dextrose', 'Hospira, Inc.', 33, 35, 294.74, 'Joint replaced ankle');
insert into Product (id, `Name`, Description, CategoryID, SubCategoryID, SalePrice, Notes) values (10000100, 'Uncoated Aspirin', 'WOONSOCKET PRESCRIPTION CENTER,INCORPORATED', 40, 40, 151.75, 'Acoustic nerve disorders');

insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000001, 13, 42.97, 2);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000002, 10, 57.96, 22);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000003, 3, 37.14, 27);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000004, 15, 115.34, 81);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000005, 10, 39.75, 48);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000006, 1, 168.73, 55);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000007, 13, 131.31, 82);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000008, 4, 89.77, 30);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000009, 3, 197.3, 3);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000010, 3, 57.82, 89);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000011, 8, 239.8, 87);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000012, 12, 167.64, 31);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000013, 9, 246.91, 6);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000014, 4, 108.26, 62);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000015, 11, 57.5, 78);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000016, 4, 97.73, 52);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000017, 4, 146.7, 22);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000018, 2, 43.75, 57);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000019, 5, 203.96, 49);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000020, 8, 144.98, 80);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000021, 15, 87.06, 79);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000022, 7, 115.61, 89);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000023, 12, 240.64, 26);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000024, 5, 62.54, 38);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000025, 2, 144.07, 17);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000026, 10, 66.67, 12);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000027, 14, 76.67, 30);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000028, 11, 132.56, 32);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000029, 9, 50.21, 22);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000030, 8, 42.88, 71);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000031, 9, 74.85, 13);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000032, 2, 163.15, 36);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000033, 11, 176.06, 78);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000034, 12, 227.81, 73);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000035, 4, 50.92, 24);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000036, 14, 161.69, 57);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000037, 5, 152.11, 57);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000038, 1, 68.8, 87);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000039, 14, 249.13, 20);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000040, 3, 200.66, 38);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000041, 14, 34.04, 78);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000042, 14, 113.09, 3);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000043, 13, 86.36, 6);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000044, 8, 130.24, 15);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000045, 5, 234.31, 27);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000046, 4, 212.85, 93);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000047, 9, 174.77, 29);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000048, 8, 39.13, 63);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000049, 5, 107.05, 52);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000050, 5, 176.98, 47);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000051, 10, 87.03, 2);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000052, 11, 91.8, 44);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000053, 4, 172.72, 49);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000054, 4, 43.41, 26);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000055, 7, 234.32, 80);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000056, 10, 228.84, 15);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000057, 13, 97.51, 38);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000058, 13, 59.62, 59);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000059, 4, 76.23, 1);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000060, 7, 81.37, 66);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000061, 2, 116.45, 57);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000062, 15, 157.19, 23);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000063, 7, 195.08, 9);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000064, 1, 245.34, 86);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000065, 10, 197.31, 74);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000066, 10, 43.26, 46);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000067, 7, 38.09, 85);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000068, 4, 210.4, 49);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000069, 11, 179.01, 62);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000070, 2, 95.32, 28);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000071, 13, 57.94, 81);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000072, 9, 43.23, 4);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000073, 1, 207.8, 92);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000074, 4, 177.32, 23);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000075, 12, 80.77, 53);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000076, 14, 190.59, 70);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000077, 15, 212.46, 77);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000078, 8, 218.67, 38);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000079, 6, 94.85, 9);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000080, 10, 160.92, 36);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000081, 10, 49.02, 91);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000082, 7, 34.11, 12);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000083, 5, 246.67, 26);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000084, 9, 106.71, 63);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000085, 10, 96.77, 17);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000086, 2, 69.57, 50);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000087, 13, 53.99, 22);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000088, 1, 67.42, 2);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000089, 12, 208.44, 26);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000090, 5, 199.62, 12);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000091, 13, 55.82, 70);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000092, 12, 45.3, 32);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000093, 14, 91.55, 23);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000094, 5, 134.22, 54);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000095, 14, 245.83, 47);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000096, 1, 169.16, 64);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000097, 7, 74.5, 41);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000098, 1, 99.37, 91);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000099, 4, 152.4, 42);
insert into Product_Supplier (ProductID, SupplierID, UnitCost, Quantity) values (10000100, 11, 42.39, 89);

update product
set Quantity = 9 + CEIL(RAND() * 90)
where ID <> 0;

update product_supplier ps, product p 
set ps.unitcost = ROUND(p.saleprice - (p.saleprice * 0.2), 2)
where ps.ProductID = p.ID AND unitcost > saleprice;

alter table product_supplier
drop column Quantity;

/*-------------------------- PROMOTION -----------------------------*/
insert into Promotion (DiscountValue) values (5.00);
insert into Promotion (DiscountValue) values (10.00);
insert into Promotion (DiscountValue) values (20.00);
insert into Promotion (DiscountPercent) values (25);
insert into Promotion (DiscountPercent) values (50);

/*-------------------------- POSITIONS -----------------------------*/
insert into Positions (`Name`, Description) values ('Sales Associate', 'Assists customers and Maintains store display');
insert into Positions (`Name`, Description) values ('Cashier', 'Handles cash register and process transactions');
insert into Positions (`Name`, Description) values ('Manager', 'Manages employees and store operations');
insert into Positions (`Name`, Description) values ('Owner', 'Person who owns store and manages its performance');
insert into Positions (`Name`, Description) values ('Accountant', 'Manages stores account related work, ex: taxes');

/*-------------------------- EMPLOYEE -----------------------------*/
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Jean', 'Ross', '5 West Trail', 'Hamilton', 'Ontario', 'y9E 6h6', '658-082-9509', '714-792-6349', null, '4', 'Casual ', 'jross0', 'r', '2014-11-23', null);

insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Wayne', 'Chavez', '439 Almo Pass', 'Hamilton', 'Ontario', 'd0F 7V2', '827-631-4796', '597-451-9742', null, '5', 'Part Time ', 'wchavez1', 'c', '2014-10-01', null);

insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Diane', 'Ward', '6516 Hallows Junction', 'Hamilton', 'Ontario', 'P6q 5I6', '241-553-3824', '147-251-3652', 'dward2@mysql.com', '5', 'Casual ', 'dward2', 'P', '2015-07-25', null);

insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Melissa', 'Patterson', '783 Warbler Lane', 'Hamilton', 'Ontario', 'w0t 7D2', null, '514-116-6313', 'mpatterson3@histats.com', '5', 'Casual ', 'mpatterson3', 'V', '2015-03-01', '2015-12-30');

insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Andrew', 'Shaw', '87142 Maryland Avenue', 'Ajax', 'Ontario', 'H1a 2w5', null, '098-060-1922', 'ashaw4@devhub.com', '5', 'Casual ', 'ashaw4', 'A', '2015-12-26', null);





insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Adam', 'Bowman', '140 Meadow Ridge Place', 'Hamilton', 'Ontario', 's4U 7u2', null, '123-162-8681', null, '3', 'Casual ', 'abowman5', 'Z', '2015-12-14', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Eugene', 'Ruiz', '7 Superior Way', 'Hamilton', 'Ontario', 'U8B 2s5', '947-463-1407', '914-743-6033', null, '5', 'Part Time ', 'eruiz6', 't', '2014-11-21', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Linda', 'Ray', '9 Schiller Way', 'Ajax', 'Ontario', 'b7m 4X8', '321-376-0530', '564-943-7241', 'lray7@github.io', '4', 'Casual ', 'lray7', 'e', '2014-12-18', '2016-01-13');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Phyllis', 'Gonzalez', '4037 Crest Line Circle', 'Orangeville', 'Ontario', 'h1C 5z4', '776-145-9449', null, 'pgonzalez8@huffingtonpost.com', '5', 'Part Time ', 'pgonzalez8', 'B', '2015-02-19', '2015-11-27');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Peter', 'Hicks', '6976 Schiller Crossing', 'Orangeville', 'Ontario', 'v8F 5N0', '430-051-8662', null, 'phicks9@ftc.gov', '5', 'Part Time ', 'phicks9', 'j', '2014-11-28', '2015-11-14');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Denise', 'Cooper', '5734 Arkansas Street', 'Ajax', 'Ontario', 'v0L 4e3', '828-593-0125', '756-438-1539', null, '4', 'Full Time ', 'dcoopera', 'r', '2015-05-03', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('John', 'Henderson', '18 Bobwhite Junction', 'Hamilton', 'Ontario', 'N8Z 4P8', '113-122-2004', null, 'jhendersonb@wikia.com', '5', 'Casual ', 'jhendersonb', 'z', '2015-07-15', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Lisa', 'Shaw', '9790 Rockefeller Junction', 'Hamilton', 'Ontario', 'j5w 4R0', '343-295-0118', '603-641-8736', null, '5', 'Casual ', 'lshawc', 'm', '2015-10-26', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Mark', 'Gordon', '1516 Prentice Hill', 'Hamilton', 'Ontario', 'E2N 0y1', '956-941-4460', '210-556-3446', null, '5', 'Casual ', 'mgordond', 'J', '2014-12-11', '2015-12-17');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Roger', 'Bennett', '1 Carberry Plaza', 'Ajax', 'Ontario', 'c4V 4m0', '641-342-7364', null, null, '5', 'Casual ', 'rbennette', 'R', '2015-02-04', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Martha', 'Alvarez', '6092 Miller Lane', 'Hamilton', 'Ontario', 'i6w 3y5', '137-811-2645', '309-999-6010', 'malvarezf@engadget.com', '4', 'Casual ', 'malvarezf', 'H', '2015-07-13', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Fred', 'Perry', '118 Lindbergh Way', 'Orangeville', 'Ontario', 'b9X 9X1', '918-085-0680', null, 'fperryg@state.tx.us', '5', 'Part Time ', 'fperryg', 'r', '2014-11-05', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Carolyn', 'Hicks', '316 Crowley Court', 'Orangeville', 'Ontario', 'g3j 3Y8', '515-570-4504', '531-547-6964', null, '4', 'Casual ', 'chicksi', 'b', '2014-11-13', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Jason', 'Pierce', '33 Memorial Lane', 'Hamilton', 'Ontario', 'f6i 3q3', '795-342-5865', '164-434-1476', null, '4', 'Casual ', 'jpiercej', 'i', '2015-12-28', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Kathryn', 'Powell', '447 Morning Circle', 'Orangeville', 'Ontario', 'M1K 4t9', '624-281-0680', '313-127-1944', null, '3', 'Casual ', 'kpowellk', 'o', '2015-08-07', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Brenda', 'Baker', '301 Talisman Road', 'Ajax', 'Ontario', 'e1V 9C3', '442-012-3228', '017-135-8359', null, '3', 'Full Time ', 'bbakerl', 'w', '2015-02-12', '2015-12-14');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Rose', 'Little', '0 Transport Point', 'Hamilton', 'Ontario', 'K3z 9b6', '866-047-3057', null, null, '5', 'Full Time ', 'rlittlem', 'A', '2015-01-09', '2015-12-07');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('George', 'Cruz', '0 Gina Center', 'Hamilton', 'Ontario', 'j9f 0h7', '685-131-0180', null, null, '3', 'Full Time ', 'gcruzn', 'C', '2014-12-16', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Jeremy', 'Mason', '8 Sycamore Center', 'Orangeville', 'Ontario', 'X9X 8H2', '213-244-1460', '217-912-9031', null, '5', 'Full Time ', 'jmasono', 'P', '2015-08-12', '2015-11-04');


/*-------------------------- TRANSACTION -----------------------------*/
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-06-01 05:10:11","390.28",ROUND(50.7364,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",23);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-11-12 05:15:48","333.76",ROUND(43.3888,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",11);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-04-03 22:48:44","6.81",ROUND(0.8853,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",13);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-12-15 19:35:47","8.90",ROUND(1.157,2),ROUND(SubTotal + Tax,2),"Payment","Credit Card",18);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-11-07 10:24:37","512.13",ROUND(66.5769,2),ROUND(SubTotal + Tax,2),"Payment","Credit Card",10);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-07-28 20:49:51","142.05",ROUND(18.4665,2),ROUND(SubTotal + Tax,2),"Sale","Cash",5);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-10-17 04:23:11","283.21",ROUND(36.8173,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",13);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-05-08 11:51:38","234.56",ROUND(30.4928,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",22);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-05-16 11:52:36","4.93",ROUND(0.6409,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",3);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2014-12-22 17:15:25","35.15",ROUND(4.5695,2),ROUND(SubTotal + Tax,2),"Payment","Cash",3);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-06-19 17:29:09","234.83",ROUND(30.5279,2),ROUND(SubTotal + Tax,2),"Payment","Cash",15);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-12-12 10:10:06","542.76",ROUND(70.5588,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",2);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-11-07 05:47:45","76.74",ROUND(9.9762,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",3);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-12-31 06:28:54","82.40",ROUND(10.712,2),ROUND(SubTotal + Tax,2),"Payment","Cash",4);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-01-31 12:02:40","719.14",ROUND(93.4882,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",17);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-01-08 10:18:05","720.66",ROUND(93.6858,2),ROUND(SubTotal + Tax,2),"Payment","Cash",15);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-08-28 09:01:34","716.12",ROUND(93.0956,2),ROUND(SubTotal + Tax,2),"Sale","Cash",12);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-06-26 19:35:51","670.79",ROUND(87.2027,2),ROUND(SubTotal + Tax,2),"Sale","Cash",19);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-11-02 06:11:03","441.24",ROUND(57.3612,2),ROUND(SubTotal + Tax,2),"Sale","Cash",8);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-11-20 12:42:28","453.54",ROUND(58.9602,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",19);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-12-01 15:45:25","424.82",ROUND(55.2266,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",8);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-06-29 14:14:39","254.49",ROUND(33.0837,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",21);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-11-18 19:55:44","309.63",ROUND(40.2519,2),ROUND(SubTotal + Tax,2),"Payment","Visa Card",10);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-01-16 14:37:25","705.35",ROUND(91.6955,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",20);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-08-04 23:15:55","8.87",ROUND(1.1531,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",14);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-11-14 00:27:08","398.97",ROUND(51.8661,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",5);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-04-03 01:53:41","137.03",ROUND(17.8139,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",13);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-09-24 09:25:11","319.86",ROUND(41.5818,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",2);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-09-03 20:12:07","649.56",ROUND(84.4428,2),ROUND(SubTotal + Tax,2),"Sale","Cash",25);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-06-07 15:36:01","219.20",ROUND(28.496,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",8);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2014-11-01 10:19:09","473.79",ROUND(61.5927,2),ROUND(SubTotal + Tax,2),"Sale","Cash",25);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-06-27 06:10:38","604.08",ROUND(78.5304,2),ROUND(SubTotal + Tax,2),"Payment","Cash",17);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-07-19 21:36:16","418.98",ROUND(54.4674,2),ROUND(SubTotal + Tax,2),"Payment","Master Card",6);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2014-12-16 13:22:37","728.06",ROUND(94.6478,2),ROUND(SubTotal + Tax,2),"Sale","Cash",7);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-05-02 09:27:04","341.40",ROUND(44.382,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",16);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-01-14 08:55:34","567.88",ROUND(73.8244,2),ROUND(SubTotal + Tax,2),"Payment","Cash",5);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-12-07 06:24:50","511.21",ROUND(66.4573,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",18);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-05-20 22:44:33","518.89",ROUND(67.4557,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",2);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-03-18 03:27:20","597.59",ROUND(77.6867,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",2);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-12-27 00:56:13","474.18",ROUND(61.6434,2),ROUND(SubTotal + Tax,2),"Payment","Cash",17);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-12-29 12:38:41","447.47",ROUND(58.1711,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",21);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-10-12 19:41:56","42.12",ROUND(5.4756,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",20);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-03-12 05:07:18","781.66",ROUND(101.6158,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",14);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-04-22 16:30:23","194.59",ROUND(25.2967,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",20);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-11-23 01:16:12","563.38",ROUND(73.2394,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",7);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-05-30 13:10:32","645.42",ROUND(83.9046,2),ROUND(SubTotal + Tax,2),"Payment","Credit Card",5);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-01-17 04:34:42","322.67",ROUND(41.9471,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",12);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-01-25 02:32:49","170.03",ROUND(22.1039,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",17);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-02-20 08:49:18","374.13",ROUND(48.6369,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",24);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-01-29 20:45:04","99.85",ROUND(12.9805,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",11);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-05-06 23:55:49","23.54",ROUND(3.0602,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",20);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-02-29 20:25:11","500.99",ROUND(65.1287,2),ROUND(SubTotal + Tax,2),"Sale","Cash",14);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-04-30 23:22:12","212.83",ROUND(27.6679,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",9);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-10-24 08:55:38","282.91",ROUND(36.7783,2),ROUND(SubTotal + Tax,2),"Sale","Cash",10);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2014-10-15 19:19:36","257.60",ROUND(33.488,2),ROUND(SubTotal + Tax,2),"Sale","Cash",24);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-05-28 04:23:32","509.33",ROUND(66.2129,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",13);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-01-20 12:15:54","648.63",ROUND(84.3219,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",1);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-11-05 11:39:01","6.26",ROUND(0.8138,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",3);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-08-17 02:48:55","217.18",ROUND(28.2334,2),ROUND(SubTotal + Tax,2),"Payment","Visa Card",16);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-04-19 16:45:00","196.61",ROUND(25.5593,2),ROUND(SubTotal + Tax,2),"Payment","Credit Card",15);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-11-25 17:29:30","442.74",ROUND(57.5562,2),ROUND(SubTotal + Tax,2),"Payment","Cash",2);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-03-10 04:16:19","253.04",ROUND(32.8952,2),ROUND(SubTotal + Tax,2),"Payment","Credit Card",21);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-10-02 10:47:36","754.66",ROUND(98.1058,2),ROUND(SubTotal + Tax,2),"Sale","Cash",3);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-07-25 08:27:11","299.59",ROUND(38.9467,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",11);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-01-17 23:30:36","642.40",ROUND(83.512,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",6);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-01-10 05:23:46","642.39",ROUND(83.5107,2),ROUND(SubTotal + Tax,2),"Sale","Cash",9);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-09-12 13:00:24","627.65",ROUND(81.5945,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",9);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-04-05 16:41:28","298.29",ROUND(38.7777,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",1);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-07-28 20:54:36","12.69",ROUND(1.6497,2),ROUND(SubTotal + Tax,2),"Sale","Cash",10);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2017-01-05 03:12:08","594.34",ROUND(77.2642,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",6);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-08-06 06:46:19","190.02",ROUND(24.7026,2),ROUND(SubTotal + Tax,2),"Payment","Cash",9);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-09-05 04:50:57","85.20",ROUND(11.076,2),ROUND(SubTotal + Tax,2),"Sale","Cash",12);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2014-11-10 08:07:32","10.54",ROUND(1.3702,2),ROUND(SubTotal + Tax,2),"Payment","Master Card",4);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-02-10 16:58:16","623.83",ROUND(81.0979,2),ROUND(SubTotal + Tax,2),"Sale","Cash",4);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-11-07 14:47:27","259.62",ROUND(33.7506,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",14);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-01-06 00:50:40","342.75",ROUND(44.5575,2),ROUND(SubTotal + Tax,2),"Sale","Cash",12);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-02-25 09:03:31","663.64",ROUND(86.2732,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",23);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-03-06 14:03:45","552.79",ROUND(71.8627,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",19);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-01-24 15:12:51","754.36",ROUND(98.0668,2),ROUND(SubTotal + Tax,2),"Sale","Cash",25);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-04-28 08:08:37","720.72",ROUND(93.6936,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",25);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-07-23 05:41:06","721.39",ROUND(93.7807,2),ROUND(SubTotal + Tax,2),"Sale","Cash",7);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-01-21 05:28:50","356.92",ROUND(46.3996,2),ROUND(SubTotal + Tax,2),"Payment","Master Card",24);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-05-28 08:33:01","732.50",ROUND(95.225,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",12);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-11-03 05:27:48","13.93",ROUND(1.8109,2),ROUND(SubTotal + Tax,2),"Payment","Cash",21);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-08-01 07:51:50","464.14",ROUND(60.3382,2),ROUND(SubTotal + Tax,2),"Payment","Credit Card",14);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-06-27 14:47:34","382.22",ROUND(49.6886,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",15);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-03-25 23:20:11","270.60",ROUND(35.178,2),ROUND(SubTotal + Tax,2),"Sale","Cash",4);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-03-29 16:32:15","598.10",ROUND(77.753,2),ROUND(SubTotal + Tax,2),"Sale","Cash",16);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-06-04 10:05:29","488.90",ROUND(63.557,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",15);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-01-13 20:29:44","215.33",ROUND(27.9929,2),ROUND(SubTotal + Tax,2),"Sale","Cash",20);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-09-29 13:13:59","292.84",ROUND(38.0692,2),ROUND(SubTotal + Tax,2),"Payment","Visa Card",21);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-09-03 10:28:44","411.44",ROUND(53.4872,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",6);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-03-07 15:31:09","612.44",ROUND(79.6172,2),ROUND(SubTotal + Tax,2),"Sale","Master Card",9);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-10-07 19:59:39","202.27",ROUND(26.2951,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",19);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-09-08 11:26:46","322.31",ROUND(41.9003,2),ROUND(SubTotal + Tax,2),"Sale","Visa Card",4);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-03-22 23:00:52","335.32",ROUND(43.5916,2),ROUND(SubTotal + Tax,2),"Sale","Credit Card",6);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-07-28 02:19:23","668.34",ROUND(86.8842,2),ROUND(SubTotal + Tax,2),"Payment","Cash",20);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-03-14 03:13:56","709.35",ROUND(92.2155,2),ROUND(SubTotal + Tax,2),"Payment","Master Card",20);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2016-06-25 18:50:07","728.00",ROUND(94.64,2),ROUND(SubTotal + Tax,2),"Sale","Cash",12);
INSERT INTO `Transaction` (`CreateDate`,`SubTotal`,`Tax`,`Total`,`TransactionType`,`Method`,`EmployeeID`) VALUES ("2015-02-13 15:12:14","163.75",ROUND(21.2875,2),ROUND(SubTotal + Tax,2),"Payment","Credit Card",17);

/*-------------------------- TRANSACTION_RECORD -----------------------------*/
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 50,10000018,3, saleprice from product where product.ID = 10000018;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 32,10000023,2, saleprice from product where product.ID = 10000023;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 88,10000010,1, saleprice from product where product.ID = 10000010;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 98,10000006,1, saleprice from product where product.ID = 10000006;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 89,10000003,3, saleprice from product where product.ID = 10000003;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 38,10000022,1, saleprice from product where product.ID = 10000022;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 9,10000007,1, saleprice from product where product.ID = 10000007;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 56,10000013,2, saleprice from product where product.ID = 10000013;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 37,10000016,2, saleprice from product where product.ID = 10000016;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 6,10000005,5, saleprice from product where product.ID = 10000005;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 38,10000021,3, saleprice from product where product.ID = 10000021;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 64,10000015,5, saleprice from product where product.ID = 10000015;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 39,10000005,2, saleprice from product where product.ID = 10000005;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 7,10000014,2, saleprice from product where product.ID = 10000014;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 97,10000008,1, saleprice from product where product.ID = 10000008;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 6,10000021,4, saleprice from product where product.ID = 10000021;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 14,10000015,4, saleprice from product where product.ID = 10000015;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 46,10000023,3, saleprice from product where product.ID = 10000023;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 4,10000005,2, saleprice from product where product.ID = 10000005;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 27,10000025,3, saleprice from product where product.ID = 10000025;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 27,10000011,1, saleprice from product where product.ID = 10000011;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 24,10000016,5, saleprice from product where product.ID = 10000016;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 18,10000021,2, saleprice from product where product.ID = 10000021;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 89,10000004,2, saleprice from product where product.ID = 10000004;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 11,10000018,3, saleprice from product where product.ID = 10000018;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 55,10000023,2, saleprice from product where product.ID = 10000023;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 29,10000018,3, saleprice from product where product.ID = 10000018;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 76,10000009,1, saleprice from product where product.ID = 10000009;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 70,10000007,4, saleprice from product where product.ID = 10000007;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 39,10000008,5, saleprice from product where product.ID = 10000008;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 90,10000008,2, saleprice from product where product.ID = 10000008;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 64,10000017,2, saleprice from product where product.ID = 10000017;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 70,10000005,3, saleprice from product where product.ID = 10000005;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 19,10000017,3, saleprice from product where product.ID = 10000017;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 3,10000021,5, saleprice from product where product.ID = 10000021;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 33,10000004,2, saleprice from product where product.ID = 10000004;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 22,10000023,1, saleprice from product where product.ID = 10000023;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 54,10000008,5, saleprice from product where product.ID = 10000008;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 61,10000018,5, saleprice from product where product.ID = 10000018;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 95,10000016,4, saleprice from product where product.ID = 10000016;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 27,10000017,4, saleprice from product where product.ID = 10000017;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 53,10000009,4, saleprice from product where product.ID = 10000009;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 91,10000002,4, saleprice from product where product.ID = 10000002;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 42,10000010,3, saleprice from product where product.ID = 10000010;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 100,10000020,5, saleprice from product where product.ID = 10000020;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 94,10000021,5, saleprice from product where product.ID = 10000021;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 33,10000009,1, saleprice from product where product.ID = 10000009;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 70,10000002,3, saleprice from product where product.ID = 10000002;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 88,10000018,5, saleprice from product where product.ID = 10000018;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 92,10000014,3, saleprice from product where product.ID = 10000014;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 56,10000006,3, saleprice from product where product.ID = 10000006;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 44,10000002,1, saleprice from product where product.ID = 10000002;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 57,10000017,3, saleprice from product where product.ID = 10000017;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 37,10000012,3, saleprice from product where product.ID = 10000012;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 46,10000014,2, saleprice from product where product.ID = 10000014;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 46,10000024,3, saleprice from product where product.ID = 10000024;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 31,10000001,4, saleprice from product where product.ID = 10000001;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 67,10000011,4, saleprice from product where product.ID = 10000011;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 12,10000010,2, saleprice from product where product.ID = 10000010;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 4,10000020,1, saleprice from product where product.ID = 10000020;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 72,10000010,3, saleprice from product where product.ID = 10000010;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 96,10000021,4, saleprice from product where product.ID = 10000021;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 44,10000025,3, saleprice from product where product.ID = 10000025;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 73,10000019,1, saleprice from product where product.ID = 10000019;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 25,10000005,2, saleprice from product where product.ID = 10000005;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 91,10000024,1, saleprice from product where product.ID = 10000024;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 59,10000022,1, saleprice from product where product.ID = 10000022;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 67,10000005,5, saleprice from product where product.ID = 10000005;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 24,10000022,5, saleprice from product where product.ID = 10000022;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 13,10000013,5, saleprice from product where product.ID = 10000013;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 21,10000012,2, saleprice from product where product.ID = 10000012;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 41,10000009,1, saleprice from product where product.ID = 10000009;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 28,10000006,4, saleprice from product where product.ID = 10000006;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 47,10000017,3, saleprice from product where product.ID = 10000017;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 98,10000012,2, saleprice from product where product.ID = 10000012;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 52,10000022,2, saleprice from product where product.ID = 10000022;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 34,10000020,4, saleprice from product where product.ID = 10000020;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 84,10000010,1, saleprice from product where product.ID = 10000010;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 78,10000003,2, saleprice from product where product.ID = 10000003;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 56,10000015,3, saleprice from product where product.ID = 10000015;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 98,10000022,3, saleprice from product where product.ID = 10000022;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 57,10000022,3, saleprice from product where product.ID = 10000022;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 12,10000005,1, saleprice from product where product.ID = 10000005;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 1,10000020,2, saleprice from product where product.ID = 10000020;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 65,10000025,4, saleprice from product where product.ID = 10000025;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 6,10000016,2, saleprice from product where product.ID = 10000016;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 84,10000016,2, saleprice from product where product.ID = 10000016;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 42,10000012,3, saleprice from product where product.ID = 10000012;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 86,10000010,5, saleprice from product where product.ID = 10000010;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 22,10000006,1, saleprice from product where product.ID = 10000006;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 88,10000021,4, saleprice from product where product.ID = 10000021;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 30,10000012,3, saleprice from product where product.ID = 10000012;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 65,10000014,3, saleprice from product where product.ID = 10000014;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 87,10000008,3, saleprice from product where product.ID = 10000008;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 93,10000022,2, saleprice from product where product.ID = 10000022;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 25,10000014,1, saleprice from product where product.ID = 10000014;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 58,10000024,5, saleprice from product where product.ID = 10000024;

UPDATE Transaction t 
SET SubTotal = (SELECT IFNULL(SUM(UnitPrice * QuantitySold), 0) 
			FROM TransactionRecord tr WHERE tr.TransactionId = t.ID);

UPDATE Transaction t 
SET Tax = ROUND(SubTotal * 0.13, 2),
    Total = ROUND(SubTotal + Tax, 2);
	
/*-------------------------- ORDER AND PAYMENT RELATED CONTENT -----------------------------*/
insert into Invoice (ID, ReceivedDate, SupplierID) values (1, '2015-09-15 18:22:47', 1);
insert into Invoice (ID, ReceivedDate, SupplierID) values (2, '2015-10-07 03:45:23', 1);
insert into Invoice (ID, ReceivedDate, SupplierID) values (3, '2016-01-11 06:08:44', 1);
insert into Invoice (ID, ReceivedDate, SupplierID) values (4, '2015-09-13 08:19:41', 2);
insert into Invoice (ID, ReceivedDate, SupplierID) values (5, '2015-10-10 17:01:36', 2);
insert into Invoice (ID, ReceivedDate, SupplierID) values (6, '2015-10-29 07:15:12', 2);
insert into Invoice (ID, ReceivedDate, SupplierID) values (7, '2015-09-15 06:00:54', 3);
insert into Invoice (ID, ReceivedDate, SupplierID) values (8, '2015-11-28 23:58:06', 3);
insert into Invoice (ID, ReceivedDate, SupplierID) values (9, '2016-01-05 18:08:27', 3);
insert into Invoice (ID, ReceivedDate, SupplierID) values (10, '2015-09-12 17:33:48', 4);
insert into Invoice (ID, ReceivedDate, SupplierID) values (11, '2015-10-31 19:44:19', 4);
insert into Invoice (ID, ReceivedDate, SupplierID) values (12, '2015-11-07 18:00:46', 4);
insert into Invoice (ID, ReceivedDate, SupplierID) values (13, '2015-09-12 20:43:00', 5);
insert into Invoice (ID, ReceivedDate, SupplierID) values (14, '2015-12-02 07:00:57', 5);
insert into Invoice (ID, ReceivedDate, SupplierID) values (15, '2016-01-25 10:27:10', 5);
insert into Invoice (ID, ReceivedDate, SupplierID) values (16, '2015-09-17 22:52:22', 6);
insert into Invoice (ID, ReceivedDate, SupplierID) values (17, '2016-01-13 14:25:14', 7);
insert into Invoice (ID, ReceivedDate, SupplierID) values (18, '2016-01-25 23:49:13', 7);
insert into Invoice (ID, ReceivedDate, SupplierID) values (19, '2015-10-02 22:19:05', 8);
insert into Invoice (ID, ReceivedDate, SupplierID) values (20, '2016-02-03 19:16:29', 8);
insert into Invoice (ID, ReceivedDate, SupplierID) values (21, '2015-11-29 12:10:56', 9);
insert into Invoice (ID, ReceivedDate, SupplierID) values (22, '2016-01-05 06:30:06', 10);
insert into Invoice (ID, ReceivedDate, SupplierID) values (23, '2015-09-30 02:02:08', 11);
insert into Invoice (ID, ReceivedDate, SupplierID) values (24, '2015-10-20 23:11:27', 11);
insert into Invoice (ID, ReceivedDate, SupplierID) values (25, '2015-11-04 22:47:46', 11);
insert into Invoice (ID, ReceivedDate, SupplierID) values (26, '2015-10-31 11:36:00', 12);
insert into Invoice (ID, ReceivedDate, SupplierID) values (27, '2015-12-26 05:19:20', 12);
insert into Invoice (ID, ReceivedDate, SupplierID) values (28, '2016-01-20 06:09:42', 12);
insert into Invoice (ID, ReceivedDate, SupplierID) values (29, '2015-10-04 04:26:38', 13);
insert into Invoice (ID, ReceivedDate, SupplierID) values (30, '2015-11-21 09:24:52', 13);
insert into Invoice (ID, ReceivedDate, SupplierID) values (31, '2016-01-21 10:27:04', 13);
insert into Invoice (ID, ReceivedDate, SupplierID) values (32, '2015-11-25 18:04:47', 14);
insert into Invoice (ID, ReceivedDate, SupplierID) values (33, '2015-09-30 13:52:56', 15);

update Invoice
set Sup_InvoiceID = ID
where ID <> 0;

insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 2, '2015-10-30 11:22:04', Date_Add(CreateDate, INTERVAL 2 WEEK), 952.84, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (8, 21, '2015-09-17 07:14:15', Date_Add(CreateDate, INTERVAL 2 WEEK), 1036.41, 985.89);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (11, 18, '2015-10-06 23:11:27', Date_Add(CreateDate, INTERVAL 2 WEEK), 593.24, 793.86);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (12, 25, '2016-01-06 06:09:42', Date_Add(CreateDate, INTERVAL 2 WEEK), 954.96, 349.4);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (2, 23, '2015-10-15 07:15:12', Date_Add(CreateDate, INTERVAL 2 WEEK), 396.21, 608.83);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (11, 11, '2015-09-23 14:58:51', Date_Add(CreateDate, INTERVAL 2 WEEK), 318.81, 337.75);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (4, 23, '2015-08-29 17:33:48', Date_Add(CreateDate, INTERVAL 2 WEEK), 557.2, 369.18);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (8, 1, '2015-09-18 22:19:05', Date_Add(CreateDate, INTERVAL 2 WEEK), 859.21, 995.62);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (5, 11, '2015-11-18 07:00:57', Date_Add(CreateDate, INTERVAL 2 WEEK), 1029.19, 1113.63);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (8, 4, '2016-01-20 19:16:29', Date_Add(CreateDate, INTERVAL 2 WEEK), 973.01, 704.18);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 14, '2015-09-20 04:26:38', Date_Add(CreateDate, INTERVAL 2 WEEK), 841.81, 369.19);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (7, 6, '2015-12-30 14:25:14', Date_Add(CreateDate, INTERVAL 2 WEEK), 701.68, 462.3);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (10, 1, '2015-12-22 06:30:06', Date_Add(CreateDate, INTERVAL 2 WEEK), 1045.27, 375.21);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (2, 11, '2016-01-09 12:26:53', Date_Add(CreateDate, INTERVAL 2 WEEK), 696.69, 1127.78);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (2, 20, '2015-09-26 17:01:36', Date_Add(CreateDate, INTERVAL 2 WEEK), 350.06, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (11, 15, '2015-10-21 22:47:46', Date_Add(CreateDate, INTERVAL 2 WEEK), 802.33, 877.48);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (14, 10, '2015-11-11 18:04:47', Date_Add(CreateDate, INTERVAL 2 WEEK), 557.26, 1121.14);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (6, 12, '2015-09-03 22:52:22', Date_Add(CreateDate, INTERVAL 2 WEEK), 828.97, 380.05);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (3, 13, '2015-12-22 18:08:27', Date_Add(CreateDate, INTERVAL 2 WEEK), 347.85, 832.73);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (6, 16, '2016-01-01 01:43:03', Date_Add(CreateDate, INTERVAL 2 WEEK), 1196.6, 406.36);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (2, 22, '2015-08-30 08:19:41', Date_Add(CreateDate, INTERVAL 2 WEEK), 1147.8, 1075.48);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (3, 18, '2015-11-14 23:58:06', Date_Add(CreateDate, INTERVAL 2 WEEK), 670.7, 1122.63);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 24, '2016-01-07 10:27:04', Date_Add(CreateDate, INTERVAL 2 WEEK), 996.23, 565.8);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (5, 23, '2015-08-29 20:43:00', Date_Add(CreateDate, INTERVAL 2 WEEK), 1013.68, 800.65);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (1, 24, '2015-09-01 18:22:47', Date_Add(CreateDate, INTERVAL 2 WEEK), 642.54, 599.85);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (4, 24, '2015-11-21 16:34:57', Date_Add(CreateDate, INTERVAL 2 WEEK), 805.99, 539.57);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (12, 10, '2015-10-17 11:36:00', Date_Add(CreateDate, INTERVAL 2 WEEK), 1175.87, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (4, 14, '2015-10-17 19:44:19', Date_Add(CreateDate, INTERVAL 2 WEEK), 552.12, 693.08);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (1, 2, '2015-09-23 03:45:23', Date_Add(CreateDate, INTERVAL 2 WEEK), 730.65, 856.75);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (3, 6, '2015-09-19 15:54:07', Date_Add(CreateDate, INTERVAL 2 WEEK), 1051.16, 931.97);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (12, 20, '2015-12-12 05:19:20', Date_Add(CreateDate, INTERVAL 2 WEEK), 528.2, 1096.9);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (15, 9, '2015-10-09 14:25:49', Date_Add(CreateDate, INTERVAL 2 WEEK), 752.74, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 7, '2015-11-10 18:54:07', Date_Add(CreateDate, INTERVAL 2 WEEK), 841.8, 455.83);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (6, 5, '2015-12-14 05:37:45', Date_Add(CreateDate, INTERVAL 2 WEEK), 659.49, 512.49);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (4, 12, '2015-10-24 18:00:46', Date_Add(CreateDate, INTERVAL 2 WEEK), 463.28, 954.41);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (6, 9, '2015-09-30 22:44:03', Date_Add(CreateDate, INTERVAL 2 WEEK), 552.78, 305.23);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (9, 4, '2015-11-15 12:10:56', Date_Add(CreateDate, INTERVAL 2 WEEK), 1009.22, 1050.45);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (1, 9, '2015-12-28 06:08:44', Date_Add(CreateDate, INTERVAL 2 WEEK), 1023.3, 1077.74);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (7, 8, '2016-01-11 23:49:13', Date_Add(CreateDate, INTERVAL 2 WEEK), 1150.2, 1190.95);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (3, 19, '2015-09-01 06:00:54', Date_Add(CreateDate, INTERVAL 2 WEEK), 533.16, 556.5);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (15, 25, '2015-09-16 13:52:56', Date_Add(CreateDate, INTERVAL 2 WEEK), 1144.77, 562.68);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (8, 4, '2016-01-09 09:44:40', Date_Add(CreateDate, INTERVAL 2 WEEK), 666.82, 434.0);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (5, 10, '2016-01-11 10:27:10', Date_Add(CreateDate, INTERVAL 2 WEEK), 1010.81, 410.96);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 7, '2015-11-07 09:24:52', Date_Add(CreateDate, INTERVAL 2 WEEK), 876.36, 866.0);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (11, 10, '2015-09-16 02:02:08', Date_Add(CreateDate, INTERVAL 2 WEEK), 355.53, 302.97);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (15, 10, '2015-09-24 00:29:53', Date_Add(CreateDate, INTERVAL 2 WEEK), 731.44, 650.51);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (11, 25, '2015-10-11 02:10:30', Date_Add(CreateDate, INTERVAL 2 WEEK), 569.42, 748.1);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (2, 25, '2015-10-08 17:29:05', Date_Add(CreateDate, INTERVAL 2 WEEK), 978.61, 1034.35);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (15, 16, '2015-08-26 12:12:37', Date_Add(CreateDate, INTERVAL 2 WEEK), 970.77, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (15, 20, '2015-10-08 20:42:38', Date_Add(CreateDate, INTERVAL 2 WEEK), 354.53, 946.3);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (5, 3, '2015-11-15 15:42:51', Date_Add(CreateDate, INTERVAL 2 WEEK), 544.38, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (14, 5, '2015-09-20 07:54:20', Date_Add(CreateDate, INTERVAL 2 WEEK), 931.15, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (11, 19, '2015-11-02 16:14:51', Date_Add(CreateDate, INTERVAL 2 WEEK), 931.34, 931.98);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (4, 18, '2015-12-23 02:53:12', Date_Add(CreateDate, INTERVAL 2 WEEK), 1129.91, 489.42);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (7, 12, '2015-11-16 19:15:17', Date_Add(CreateDate, INTERVAL 2 WEEK), 1098.37, 725.67);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 2, '2016-01-22 16:10:49', Date_Add(CreateDate, INTERVAL 2 WEEK), 505.07, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (7, 17, '2015-12-22 22:12:30', Date_Add(CreateDate, INTERVAL 2 WEEK), 808.32, 549.38);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 18, '2015-09-26 09:10:50', Date_Add(CreateDate, INTERVAL 2 WEEK), 839.71, 564.92);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (3, 22, '2015-10-28 05:25:01', Date_Add(CreateDate, INTERVAL 2 WEEK), 701.87, 698.37);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (4, 3, '2015-09-20 03:56:19', Date_Add(CreateDate, INTERVAL 2 WEEK), 1027.59, 1136.22);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (2, 6, '2015-11-14 06:11:25', Date_Add(CreateDate, INTERVAL 2 WEEK), 1049.91, 617.75);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (1, 5, '2015-12-14 23:54:28', Date_Add(CreateDate, INTERVAL 2 WEEK), 654.8, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (12, 23, '2015-10-26 19:26:45', Date_Add(CreateDate, INTERVAL 2 WEEK), 469.6, 858.67);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (8, 5, '2015-11-25 19:12:21', Date_Add(CreateDate, INTERVAL 2 WEEK), 316.18, 760.58);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (9, 16, '2015-11-14 07:41:25', Date_Add(CreateDate, INTERVAL 2 WEEK), 457.52, 634.27);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (4, 8, '2016-01-07 16:11:22', Date_Add(CreateDate, INTERVAL 2 WEEK), 605.43, 367.53);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (8, 22, '2015-08-31 13:46:58', Date_Add(CreateDate, INTERVAL 2 WEEK), 1152.3, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (9, 4, '2015-09-23 02:45:43', Date_Add(CreateDate, INTERVAL 2 WEEK), 1046.45, 528.97);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (11, 10, '2016-01-15 03:30:45', Date_Add(CreateDate, INTERVAL 2 WEEK), 753.38, 497.46);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (1, 11, '2015-09-28 09:15:19', Date_Add(CreateDate, INTERVAL 2 WEEK), 737.51, 1060.15);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (3, 2, '2015-09-06 14:20:52', Date_Add(CreateDate, INTERVAL 2 WEEK), 436.51, 1056.97);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (11, 25, '2015-11-10 17:18:15', Date_Add(CreateDate, INTERVAL 2 WEEK), 634.14, 502.15);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (2, 21, '2015-11-24 07:33:12', Date_Add(CreateDate, INTERVAL 2 WEEK), 1084.07, 758.3);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (10, 1, '2015-09-23 20:09:28', Date_Add(CreateDate, INTERVAL 2 WEEK), 333.61, 1155.3);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (4, 1, '2016-03-22 18:45:10', null, 898.92, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (12, 12, '2016-04-24 05:56:01', null, 393.7, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (5, 3, '2016-04-12 00:05:51', null, 303.64, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (15, 21, '2016-03-05 08:47:12', null, 397.08, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (1, 22, '2016-03-13 19:55:40', null, 544.19, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (11, 9, '2016-03-31 23:34:59', null, 861.36, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (10, 19, '2016-03-04 21:37:32', null, 931.38, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 4, '2016-02-22 13:32:55', null, 768.04, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (2, 2, '2016-03-22 10:30:05', null, 938.36, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (7, 2, '2016-01-27 20:32:09', null, 1199.58, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (3, 11, '2016-04-10 03:47:11', null, 648.49, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 7, '2016-02-15 14:55:34', null, 1009.66, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (6, 14, '2016-04-16 03:16:32', null, 478.47, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (8, 13, '2016-03-17 06:38:09', null, 648.35, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (9, 19, '2016-04-23 13:52:02', null, 505.31, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (7, 24, '2016-03-02 16:30:50', null, 990.16, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 4, '2016-04-18 18:10:54', null, 591.44, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (6, 15, '2016-01-28 20:19:04', null, 1094.82, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (10, 20, '2016-02-17 02:38:30', null, 541.53, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (13, 12, '2016-03-16 12:03:37', null, 532.61, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (2, 15, '2016-04-10 08:42:37', null, 1146.45, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (12, 3, '2016-03-01 23:22:46', null, 976.57, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (5, 6, '2016-02-23 08:04:37', null, 410.7, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (14, 13, '2016-04-23 15:01:23', null, 985.97, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (6, 2, '2016-03-08 17:36:11', null, 903.59, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (9, 5, '2016-04-15 11:45:22', null, 437.55, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (14, 13, '2016-03-16 14:16:49', null, 1112.74, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (8, 16, '2016-04-19 00:44:34', null, 499.59, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (1, 7, '2016-04-24 22:21:27', null, 833.5, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (10, 12, '2016-04-06 11:03:42', null, 966.01, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (1, 2, '2016-02-02 10:03:41', null, 472.01, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (4, 11, '2016-03-02 22:44:11', null, 650.96, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (15, 8, '2016-02-28 04:25:48', null, 664.4, null);
insert into `Order` (SupplierID, EmployeeID, CreateDate, ReceivedDate, Cost, AmountPaid) values (3, 4, '2016-02-21 10:05:31', null, 642.61, null);
	
/*-------------------------- ORDER_DETAIL -----------------------------*/
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (29,10000018,47,81,"608.78");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (12,10000006,60,57,"878.66");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (24,10000025,63,50,"814.64");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (7,10000016,29,60,"509.63");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (27,10000009,67,77,"304.23");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (31,10000020,55,96,"578.54");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (5,10000016,89,56,"612.60");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (16,10000007,57,70,"497.79");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (41,10000008,40,39,"844.54");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (18,10000009,90,55,"474.21");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (9,10000004,58,39,"426.32");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (4,10000025,26,35,"788.13");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (43,10000004,82,50,"429.30");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (5,10000002,90,54,"603.99");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (13,10000018,84,73,"936.43");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (8,10000010,92,79,"866.40");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (24,10000010,79,43,"712.93");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (5,10000006,53,78,"311.26");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (19,10000024,64,48,"515.22");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (22,10000022,30,75,"407.02");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (25,10000024,80,24,"803.38");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (23,10000003,28,42,"883.81");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (35,10000021,33,38,"705.09");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (38,10000009,73,36,"565.47");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (19,10000012,96,90,"693.71");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (21,10000022,94,39,"633.59");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (28,10000005,94,66,"825.12");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (45,10000004,79,75,"631.17");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (27,10000014,36,62,"865.67");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (15,10000003,20,29,"596.78");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (16,10000019,42,29,"860.37");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (15,10000017,93,82,"376.40");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (40,10000005,71,49,"751.18");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (16,10000024,73,89,"607.34");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (10,10000005,47,24,"492.96");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (39,10000019,99,88,"316.99");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (44,10000002,23,88,"373.27");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (35,10000003,33,67,"830.16");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (24,10000012,46,78,"957.30");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (10,10000004,62,69,"884.58");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (21,10000005,98,48,"400.44");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (3,10000015,23,27,"746.13");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (15,10000008,62,58,"824.50");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (12,10000009,83,42,"813.57");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (4,10000006,91,100,"507.68");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (10,10000019,65,44,"364.64");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (19,10000015,91,45,"397.30");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (13,10000010,50,42,"718.33");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (11,10000006,38,97,"595.64");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (40,10000003,31,75,"819.09");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (9,10000017,46,64,"422.56");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (38,10000012,92,74,"591.67");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (31,10000003,30,32,"427.30");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (17,10000024,52,34,"499.58");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (39,10000012,78,42,"849.62");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (37,10000007,85,43,"512.42");
INSERT INTO `OrderDetail` (`OrderID`,`ProductID`,`OrderedQuantity`,`ReceivedQuantity`,`Cost`) VALUES (15,10000001,22,88,"338.35");

/* ------------- Updating table to keep data valid -------------- */
update OrderDetail
set OrderedQuantity = ReceivedQuantity
where ReceivedQuantity > OrderedQuantity;

update OrderDetail o, Product p
set o.Cost = ROUND(o.ReceivedQuantity * p.SalePrice, 2) 
where o.ProductID = p.ID;

UPDATE `Order` AS o 
INNER JOIN 
(SELECT OrderID, ROUND(sum(Cost), 2) cst FROM orderDetail GROUP BY OrderID) d 
ON o.ID = d.OrderID
SET o.Cost = d.cst;

delete from `order`
where ID not in (select OrderID from OrderDetail group by OrderID);

delete from `transaction`
where SubTotal = 0;

/*------- ADDED COST COLUMN TO GET ACCURATE REVENUE REPORT -------*/
alter table transactionrecord
add UnitCost double not null default 0;

update transactionrecord tr, product_supplier p
set tr.UnitCost = p.UnitCost
where tr.ProductID = p.ProductID;

/*--------------- CONNECTING INVOICE WITH ORDERS -----------------*/
update `Order` SET InvoiceID = 24 where ID = 3;
update `Order` SET InvoiceID = 28 where ID = 4;
update `Order` SET InvoiceID = 6 where ID = 5;
update `Order` SET InvoiceID = 10 where ID = 7;
update `Order` SET InvoiceID = 19 where ID = 8;
update `Order` SET InvoiceID = 14 where ID = 9;
update `Order` SET InvoiceID = 20 where ID = 10;
update `Order` SET InvoiceID = 29 where ID = 11;
update `Order` SET InvoiceID = 17 where ID = 12;
update `Order` SET InvoiceID = 22 where ID = 13;
update `Order` SET InvoiceID = 5 where ID = 15;
update `Order` SET InvoiceID = 25 where ID = 16;
update `Order` SET InvoiceID = 32 where ID = 17;
update `Order` SET InvoiceID = 16 where ID = 18;
update `Order` SET InvoiceID = 9 where ID = 19;
update `Order` SET InvoiceID = 4 where ID = 21;
update `Order` SET InvoiceID = 8 where ID = 22;
update `Order` SET InvoiceID = 31 where ID = 23;
update `Order` SET InvoiceID = 13 where ID = 24;
update `Order` SET InvoiceID = 1 where ID = 25;
update `Order` SET InvoiceID = 26 where ID = 27;
update `Order` SET InvoiceID = 11 where ID = 28;
update `Order` SET InvoiceID = 2 where ID = 29;
update `Order` SET InvoiceID = 27 where ID = 31;
update `Order` SET InvoiceID = 12 where ID = 35;
update `Order` SET InvoiceID = 21 where ID = 37;
update `Order` SET InvoiceID = 3 where ID = 38;
update `Order` SET InvoiceID = 18 where ID = 39;
update `Order` SET InvoiceID = 7 where ID = 40;
update `Order` SET InvoiceID = 33 where ID = 41;
update `Order` SET InvoiceID = 15 where ID = 43;
update `Order` SET InvoiceID = 30 where ID = 44;
update `Order` SET InvoiceID = 23 where ID = 45;


/*Adding column’s in Employee, keeping track of who created the account, terminated or altered the employee record.*/
ALTER TABLE Employee ADD CreatedBy VARCHAR(30);
ALTER TABLE Employee ADD TerminatedBy VARCHAR(30);
ALTER TABLE Employee ADD AlteredBy VARCHAR(30);

/*Altering employee (had problems doing so above)*/
/*jross0:Jr@2*/
ALTER Table Employee Add Salt VARCHAR(50);
update Employee Set Salt = '[B@168eb7b3' Where ID = 1;
update Employee Set PositionID = 5 Where ID = 1;
UPDATE Employee Set Password='61d9dcd7aace91c408b7665a9c82b601c74e06087c630eca15955549a02b35af87b12b82280da3d5934fccdc1636c2255513052333a39e2984a0896c0ff2a75c' WHERE ID = 1;
/*update Employee set Email = "jross0@myseneca.ca" where ID = 1;*/

/*wchavez1:Wc@2*/
update Employee Set Salt = '[B@6247126a' Where ID = 2;
update Employee Set PositionID = 4 Where ID = 2;
UPDATE Employee Set Password='616e0f0f7055d88709c63f4a94d294dc9e1fa5304aeec308224b7b9dfeaaa6ab974e9debd806f23c158b9eacfa146256a2d1f2846f034412a9fa27a50b15de34' WHERE ID = 2;
/*update Employee set Email = "wchavez1@myseneca.ca" where ID = 2;*/

/*dward2:Dw@2*/
update Employee Set Salt = '[B@4567545' Where ID = 3;
update Employee Set PositionID = 2 Where ID = 3;
UPDATE Employee Set Password='8d13471b605c700c2ba8fc177d490ea031124d8d83fc780103882acaddaee2d1802a5d78260c98fbc3f736a9aa290349aaf7a24bf85a671551c8c35f367a6288' WHERE ID = 3;

/*mpatterson3:Mp@2*/
update Employee Set Salt = '[B@fd1d5c9' Where ID = 4;
update Employee Set PositionID = 1 Where ID = 4;
UPDATE Employee Set Password='f9c5d915dd951bf73026f25f83036f5402d6a2c2079e36d62b0d2f16ce657604dea682ed8f266c9dddb50b00ce74f216f0a2cb6cce983e383aa34bf3516a2f8d' WHERE ID = 4;
update Employee set TerminationDate = NULL where ID = 4;

/*ashaw4:As@2*/
update Employee Set Salt = '[B@13c52566' Where ID = 5;
update Employee Set PositionID = 3 Where ID = 5;
UPDATE Employee Set Password='09adc62de2603b230f99a22afc73485ce9b292ec2388e9588419e11f7c7e92dcf7f78f5347f49b1941c3b14b11ce63a5c50f2256b3f1a024e12e5d28366381c9' WHERE ID = 5;

/*All employees that are not needed, terminated by:*/
update Employee set CreatedBy = "Wayne Chavez" where ID > 0;


/* Adding a UnitCost column in product table that will be average cost from product from multiple supplier. */
alter table Product
add column UnitCost double default 0;

update Product p 
set UnitCost = (select avg(UnitCost) from product_supplier ps where ps.ProductID = p.ID);

/* Products Retuned table to have record of products that was returned and when.*/
create table ProductReturned (
	ID					int			AUTO_INCREMENT,
    TransactionID		int			not null,
    ProductID			int(8)		not null,
    ReturnedQTY			int			not null,
    DateReturned		timestamp	not null,
    
    PRIMARY KEY (ID, TransactionID, ProductID),
    FOREIGN KEY (TransactionID) References `Transaction`(ID),
    FOREIGN KEY (ProductID) References Product(ID),
    CONSTRAINT ProductsReturn_returned_qty CHECK (ReturnedQTY > 0)
);

