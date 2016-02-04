drop database if exists StoreDB;
create database StoreDB;
use StoreDB;

/*-------------------------- CATEGORY -----------------------------*/
create table Category
(
	ID				int				not null AUTO_INCREMENT,
	`Name`			varchar(35)		not null,
	Description		varchar(60),

	PRIMARY KEY(ID),
	Unique(`Name`)
);

INSERT INTO Category (`Name`) VALUES ("Clothing");
INSERT INTO Category (`Name`) VALUES ("Womenswear");
INSERT INTO Category (`Name`) VALUES ("Menswear");
INSERT INTO Category (`Name`) VALUES ("Personal Care");
INSERT INTO Category (`Name`) VALUES ("Skin Care");
INSERT INTO Category (`Name`) VALUES ("Hair Care");
INSERT INTO Category (`Name`) VALUES ("Cosmetics");
INSERT INTO Category (`Name`) VALUES ("Deodorants and Anti Perspirants");
INSERT INTO Category (`Name`) VALUES ("Soap, Bath and Shower Products");
INSERT INTO Category (`Name`) VALUES ("Household");
INSERT INTO Category (`Name`) VALUES ("Air Fresheners");
INSERT INTO Category (`Name`) VALUES ("Furniture");
INSERT INTO Category (`Name`) VALUES ("Household Cleaners");
INSERT INTO Category (`Name`) VALUES ("Consumer Electronics");
INSERT INTO Category (`Name`) VALUES ("Audio Equipment");
INSERT INTO Category (`Name`) VALUES ("Cameras and Camera Equipment");
INSERT INTO Category (`Name`) VALUES ("Lighting Equipment");
INSERT INTO Category (`Name`) VALUES ("Computer Accessories");
INSERT INTO Category (`Name`) VALUES ("Car Electronics and GPS");
INSERT INTO Category (`Name`) VALUES ("Video Games and Consoles");
INSERT INTO Category (`Name`) VALUES ("Stationery and Greeting Cards");
INSERT INTO Category (`Name`) VALUES ("Jewelry and Watches");
INSERT INTO Category (`Name`) VALUES ("Food");
INSERT INTO Category (`Name`) VALUES ("Snacks and Confectionery");
INSERT INTO Category (`Name`) VALUES ("Health Food and Sports Nutrition");
INSERT INTO Category (`Name`) VALUES ("Canned Food");
INSERT INTO Category (`Name`) VALUES ("Frozen Food");
INSERT INTO Category (`Name`) VALUES ("Meat, Poultry and Eggs");
INSERT INTO Category (`Name`) VALUES ("Fruit and Vegetables");
INSERT INTO Category (`Name`) VALUES ("Baked Goods");
INSERT INTO Category (`Name`) VALUES ("Cereals");
INSERT INTO Category (`Name`) VALUES ("Soup");
INSERT INTO Category (`Name`) VALUES ("Rice and Rice Products");
INSERT INTO Category (`Name`) VALUES ("Nuts, Seeds and Dried Fruit");
INSERT INTO Category (`Name`) VALUES ("Sugar and Sweeteners");
INSERT INTO Category (`Name`) VALUES ("Beverage");
INSERT INTO Category (`Name`) VALUES ("Soft Drinks");
INSERT INTO Category (`Name`) VALUES ("Hot Drinks");
INSERT INTO Category (`Name`) VALUES ("Bottled Water");
INSERT INTO Category (`Name`) VALUES ("Sport, Energy and Functional Drinks");
INSERT INTO Category (`Name`) VALUES ("Juice");
INSERT INTO Category (`Name`) VALUES ("Alcoholic Beverages");
INSERT INTO Category (`Name`) VALUES ("Tobacco");
INSERT INTO Category (`Name`) VALUES ("Cigarettes");
INSERT INTO Category (`Name`) VALUES ("Dairy Products");
INSERT INTO Category (`Name`) VALUES ("Milk and Cream");
INSERT INTO Category (`Name`) VALUES ("Yogurt");
INSERT INTO Category (`Name`) VALUES ("Cheese");
INSERT INTO Category (`Name`) VALUES ("Butter");

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
	
	PRIMARY KEY(ID),
	CONSTRAINT CK_Supplier_PhoneNumber check (PhoneNumber like '[0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_Supplier_MinimumOrderCost check (MinimumOrderCost > 150),
    CONSTRAINT CK_Supplier_Status check (`Status` = "Active" OR `Status` = "In-Active" OR `Status` = "InActive"),
	Unique(`Name`, Street, PhoneNumber)
);

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

/*-------------------------- CONTACT -----------------------------*/
create table Contact
(
	ID				int				not null	AUTO_INCREMENT,
	FirstName		varchar(15)		not null,
	LastName		varchar(15)		not null,
	PhoneNumber		varchar(15)		not null,
	Email			varchar(50),
	SupplierID		int				not null,

	PRIMARY KEY(ID),
	FOREIGN KEY (SupplierID) REFERENCES Supplier(ID),
	CONSTRAINT CK_Contact_PhoneNumber check (PhoneNumber like '[0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'),
	Unique(PhoneNumber)
);

INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (1,"Kyle","Suarez","1-404-990-1890","neque.pellentesque.massa@Curae.net",1);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (2,"Whilemina","Harrell","1-935-584-3889","orci.tincidunt.adipiscing@Integerin.com",12);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (3,"Jorden","Hebert","1-469-234-6519","rhoncus.Donec.est@morbi.ca",3);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (4,"Baxter","Stark","1-729-860-4675","Integer@auctorvelit.co.uk",15);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (5,"Christian","Cotton","1-201-925-5715","ornare.placerat.orci@accumsanlaoreetipsum.org",15);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (6,"Demetria","Cervantes","1-167-685-6629","Nunc.sed@diamatpretium.com",13);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (7,"Lunea","Cantu","1-552-501-5624","Cras@etrutrum.edu",13);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (8,"Marny","Hess","1-447-121-5893","Nunc.pulvinar@viverra.co.uk",14);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (9,"Elliott","Floyd","1-327-257-2761","nascetur.ridiculus@nunc.com",4);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (10,"Preston","Mccall","1-813-388-0610","libero.Integer@eget.org",4);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (11,"Bernard","Lara","1-981-450-4017","Class@interdumenimnon.ca",11);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (12,"Haley","Fox","1-261-450-5355","ut.nulla.Cras@Nullam.com",7);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (13,"Chelsea","Blake","1-226-549-2210","ultricies@quamCurabitur.edu",12);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (14,"Tyler","Mcintyre","1-789-890-1458","at.velit@velvenenatisvel.ca",9);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (15,"Genevieve","Butler","1-195-311-1140","Lorem@non.org",6);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (16,"Fulton","Buchanan","1-479-734-8332","Aliquam.nisl.Nulla@eliteratvitae.co.uk",14);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (17,"Jenna","Randolph","1-267-129-8724","vehicula.et@aaliquet.co.uk",14);
INSERT INTO Contact (ID,FirstName,LastName,PhoneNumber,Email,SupplierID) VALUES (18,"Amena","Stark","1-170-910-5565","venenatis@euodio.edu",14);

/*-------------------------- PRODUCT -----------------------------*/
create table Product
(
	ID				int(8)				not null,
	`Name`			varchar(50)			not null,	
	Description		varchar(80),
	CategoryID		int					not null,
	SubCategoryID	int,			
	UnitCost		double				not null,
	SalePrice		double				not null,
	Quantity		int					DEFAULT 0,
	SupplierID		int					not null,
	Notes			varchar(80),
	
	PRIMARY KEY(ID),
	FOREIGN KEY (CategoryID) REFERENCES Category(ID),
	FOREIGN KEY (SubCategoryID) REFERENCES Category(ID),
	FOREIGN KEY (SupplierID) REFERENCES Supplier(ID),
	CONSTRAINT CK_Product_Categories check(CategoryID != SubCategoryID),
	CONSTRAINT CK_Product_UnitCost check(UnitCost > 0),
	CONSTRAINT CK_Product_SalePrice check(SalePrice > 0),
    CONSTRAINT CK_Product_priceCheck check(SalePrice > UnitCost), /*Does not work.*/
	CONSTRAINT CK_Product_Quantity check(Quantity > 0)
);

insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000001, 'Bisacodyl', 'Second malig neo liver', 32, 45, 36.5, 137.19, 238, 9, 'Choledochoplasty');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000002, 'Lisinopril', 'Opn skl/oth fx-deep coma', 45, 22, 196.74, 157.84, 349, 1, 'Medical induction of labor');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000003, 'Triclosan', 'Ac polio NOS-type NOS', 16, 22, 24.39, 186.22, 25, 13, '[Endoscopic] polypectomy of rectum');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000004, 'Argentum Quartz', 'Late eff wrist/hand burn', 39, 8, 110.09, 178.88, 11, 4, 'Revision of stoma of small intestine');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000005, 'Sodium Sulfacetamide', 'Poxvirus infections NEC', 41, 29, 86.51, 134.29, 294, 8, null);
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000006, 'TITANIUM DIOXIDE ZINC OXIDE', 'Hodg lymph-histio pelvic', 14, 39, 81.24, 80.58, 44, 8, 'Electronystagmogram [ENG]');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000007, 'Tolcapone', 'AMI NOS, subsequent', 40, 3, 19.11, 184.47, 256, 15, 'Other salpingo-oophoroplasty');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000008, 'Phentermine Hydrochloride', 'Fracture of ilium-open', 5, 8, 59.52, 143.97, 153, 9, null);
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000009, 'RANITIDINE', 'Emotional dis child NEC', 14, 48, 44.21, 189.09, 462, 9, 'Intrathoracic lymphangiogram');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000010, 'GLYCERIN', 'Acc poison-barbiturates', 38, 24, 189.24, 76.25, 59, 15, 'Open and other sigmoidectomy');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000011, 'AMOXICILLIN', 'Jt derangment NOS-shlder', 19, 42, 93.18, 118.95, 481, 15, 'Excision or destruction of lesion of sclera');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000012, 'Dopamine HCl', 'Cataract NOS', 13, 18, 62.51, 3.28, 4, 4, 'Other local destruction or excision of renal lesion or tissue');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000013, 'DORZOLAMIDE HYDROCHLORIDE and TIMOLOL MALEATE', 'Adv eff skelet musc relx', 36, 10, 13.57, 5.29, 329, 5, 'Other incision of vulva and perineum');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000014, 'zolpidem tartrate', 'Mal neo soft tissue leg', 18, 26, 37.87, 195.66, 146, 9, 'Intravascular imaging of intrathoracic vessels');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000015, 'Acetaminophen and Phenylephrine Hydrochloride', 'Gonococcal pericarditis', 23, 29, 178.31, 96.46, 42, 5, 'Other repair of joint');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000016, 'ARNICA MONTANA', 'Anaplastic lymph multip', 17, 1, 111.22, 107.27, 365, 5, null);
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000017, 'Alendronate Sodium', 'Med exam NEC-admin purp', 17, 17, 168.64, 178.11, 283, 15, 'Microscopic examination of specimen from lung, and other thoracic specimen');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000018, 'Titanium Dioxide', 'Disorders of sacrum', 46, 21, 183.05, 197.63, 496, 14, 'Other total reconstruction of breast');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000019, 'Aspirin', 'Encountr palliative care', 29, 12, 165.34, 134.77, 238, 7, null);
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000020, 'Menthol', 'Algoneurodystrophy', 1, 47, 66.28, 13.69, 294, 11, 'Laparoscopy');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000021, 'ARNICA MONTANA', 'Drusen (degenerative)', 13, 21, 106.22, 147.48, 323, 15, 'Gastrotomy');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000022, 'BENZALKONIUM CHLORIDE', 'Cross country skiing', 11, 43, 30.79, 92.82, 257, 14, 'Reconstruction of eyelid with mucous membrane flap or graft');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000023, 'Benztropine Mesylate', 'N-traf brd/alight-mocycl', 2, 39, 31.62, 95.57, 220, 14, 'Ileostomy, not otherwise specified');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000024, 'Acetaminophen Phenylephrine HCl', 'Racquet/hand sports', 19, 19, 109.72, 53.69, 53, 13, 'Revision of ureterointestinal anastomosis');
insert into product (id, `Name`, Description, CategoryID, SubCategoryID, UnitCost, SalePrice, Quantity, SupplierID, Notes) values (10000025, 'Cefprozil', 'Loss labyrn react unilat', 9, 45, 68.78, 147.52, 161, 13, null);

update product set unitcost = saleprice - (saleprice * 0.2)
where unitcost > saleprice;

/*-------------------------- PRICE_HISTORY -----------------------------*/
create table PriceHistory
(
	EffectiveDate	timestamp		DEFAULT current_timestamp,
	ProductID		int(8)			not null,
	NewPrice		double			not null,
	
	PRIMARY KEY(EffectiveDate, ProductID),
	CONSTRAINT CK_PriceHistory_NewPrice check(NewPrice > 0)
);

DELIMITER $$
CREATE TRIGGER Product_Price_Updated 
    BEFORE UPDATE ON Product
    FOR EACH ROW 
BEGIN
IF (NEW.SalePrice != OLD.SalePrice) THEN
    INSERT INTO PriceHistory
    SET EffectiveDate = NOW(),
		ProductID = OLD.ID,
        NewPrice = NEW.SalePrice; 
END IF;
END$$
DELIMITER ;
/*-------------------------- QUALITY_ADJUSTMENT -----------------------------*/
create table QAdjustment
(
	ID			int				not null	AUTO_INCREMENT,
	ProductID	int(8)			not null,
	Quantity	int 			not null,
	Reason		varchar(25),
	ChangeDate	DATE			not null,
	
	PRIMARY KEY(ID),
	FOREIGN KEY (ProductID) REFERENCES Product(ID),
	CONSTRAINT CK_QAdjustment_Quantity check(Quantity > 0)
);

DELIMITER $$
CREATE TRIGGER Product_Quantity_Updated 
    AFTER UPDATE ON Product
    FOR EACH ROW 
BEGIN
IF (NEW.Quantity != OLD.Quantity) THEN
    INSERT INTO QAdjustment
    SET ProductID = OLD.ID,
        Quantity = NEW.Quantity,
        /*Reason = New.Reason, */
		ChangeDate = Now();
END IF;
END$$
DELIMITER ;

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

insert into Promotion (DiscountValue) values (5.00);
insert into Promotion (DiscountValue) values (10.00);
insert into Promotion (DiscountValue) values (20.00);
insert into Promotion (DiscountPercent) values (25);
insert into Promotion (DiscountPercent) values (50);

/*-------------------------- POSITIONS -----------------------------*/
create table Positions
(
	ID				int				not null AUTO_INCREMENT,
	`Name`			varchar(20)		not null,
	Description		varchar(50),
	
	PRIMARY KEY(ID)
);
insert into Positions (`Name`, Description) values ('Sales Associate', 'Assists customers and Maintains store display');
insert into Positions (`Name`, Description) values ('Cashier', 'Handles cash register and process transactions');
insert into Positions (`Name`, Description) values ('Manager', 'Manages employees and store operations');
insert into Positions (`Name`, Description) values ('Owner', 'Person who owns store and manages its performance');
insert into Positions (`Name`, Description) values ('Accountant', 'Manages stores account related work, ex: taxes');

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
	`Password`		varchar(20)		not null,
	HireDate		DATE			not null,
	TerminationDate	DATE,
	
	PRIMARY KEY(ID),
	FOREIGN KEY (PositionID) REFERENCES Positions(ID),
	Unique(UserName),
	CONSTRAINT CK_Employee_HomePhoneNumber check (HomePhoneNumber like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	CONSTRAINT CK_Employee_CellPhoneNumber check (CellPhoneNumber like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	CONSTRAINT CK_Employee_ContectNumbers check (HomePhoneNumber != CellPhoneNumber)
);

insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Jean', 'Ross', '5 West Trail', 'Hamilton', 'Ontario', 'y9E 6h6', '8-658-082-9509', '2-714-792-6349', null, '4', 'Casual ', 'jross0', 'r', '2014-11-23', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Wayne', 'Chavez', '439 Almo Pass', 'Hamilton', 'Ontario', 'd0F 7V2', '3-827-631-4796', '7-597-451-9742', null, '5', 'Part Time ', 'wchavez1', 'c', '2014-10-01', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Diane', 'Ward', '6516 Hallows Junction', 'Hamilton', 'Ontario', 'P6q 5I6', '7-241-553-3824', '5-147-251-3652', 'dward2@mysql.com', '5', 'Casual ', 'dward2', 'P', '2015-07-25', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Melissa', 'Patterson', '783 Warbler Lane', 'Hamilton', 'Ontario', 'w0t 7D2', null, '4-514-116-6313', 'mpatterson3@histats.com', '5', 'Casual ', 'mpatterson3', 'V', '2015-03-01', '2015-12-30');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Andrew', 'Shaw', '87142 Maryland Avenue', 'Ajax', 'Ontario', 'H1a 2w5', null, '1-098-060-1922', 'ashaw4@devhub.com', '5', 'Casual ', 'ashaw4', 'A', '2015-12-26', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Adam', 'Bowman', '140 Meadow Ridge Place', 'Hamilton', 'Ontario', 's4U 7u2', null, '6-123-162-8681', null, '3', 'Casual ', 'abowman5', 'Z', '2015-12-14', '2015-11-04');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Eugene', 'Ruiz', '7 Superior Way', 'Hamilton', 'Ontario', 'U8B 2s5', '4-947-463-1407', '8-914-743-6033', null, '5', 'Part Time ', 'eruiz6', 't', '2014-11-21', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Linda', 'Ray', '9 Schiller Way', 'Ajax', 'Ontario', 'b7m 4X8', '7-321-376-0530', '1-564-943-7241', 'lray7@github.io', '4', 'Casual ', 'lray7', 'e', '2014-12-18', '2016-01-13');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Phyllis', 'Gonzalez', '4037 Crest Line Circle', 'Orangeville', 'Ontario', 'h1C 5z4', '2-776-145-9449', null, 'pgonzalez8@huffingtonpost.com', '5', 'Part Time ', 'pgonzalez8', 'B', '2015-02-19', '2015-11-27');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Peter', 'Hicks', '6976 Schiller Crossing', 'Orangeville', 'Ontario', 'v8F 5N0', '4-430-051-8662', null, 'phicks9@ftc.gov', '5', 'Part Time ', 'phicks9', 'j', '2014-11-28', '2015-11-14');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Denise', 'Cooper', '5734 Arkansas Street', 'Ajax', 'Ontario', 'v0L 4e3', '4-828-593-0125', '9-756-438-1539', null, '4', 'Full Time ', 'dcoopera', 'r', '2015-05-03', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('John', 'Henderson', '18 Bobwhite Junction', 'Hamilton', 'Ontario', 'N8Z 4P8', '2-113-122-2004', null, 'jhendersonb@wikia.com', '5', 'Casual ', 'jhendersonb', 'z', '2015-07-15', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Lisa', 'Shaw', '9790 Rockefeller Junction', 'Hamilton', 'Ontario', 'j5w 4R0', '1-343-295-0118', '5-603-641-8736', null, '5', 'Casual ', 'lshawc', 'm', '2015-10-26', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Mark', 'Gordon', '1516 Prentice Hill', 'Hamilton', 'Ontario', 'E2N 0y1', '3-956-941-4460', '5-210-556-3446', null, '5', 'Casual ', 'mgordond', 'J', '2014-12-11', '2015-12-17');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Roger', 'Bennett', '1 Carberry Plaza', 'Ajax', 'Ontario', 'c4V 4m0', '6-641-342-7364', null, null, '5', 'Casual ', 'rbennette', 'R', '2015-02-04', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Martha', 'Alvarez', '6092 Miller Lane', 'Hamilton', 'Ontario', 'i6w 3y5', '7-137-811-2645', '5-309-999-6010', 'malvarezf@engadget.com', '4', 'Casual ', 'malvarezf', 'H', '2015-07-13', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Fred', 'Perry', '118 Lindbergh Way', 'Orangeville', 'Ontario', 'b9X 9X1', '6-918-085-0680', null, 'fperryg@state.tx.us', '5', 'Part Time ', 'fperryg', 'r', '2014-11-05', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Philip', 'Elliott', '2072 Veith Alley', 'Hamilton', 'Ontario', 'r0b 2o7', null, null, null, '3', 'Casual ', 'pelliotth', 'R', '2015-08-31', '2015-11-26');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Carolyn', 'Hicks', '316 Crowley Court', 'Orangeville', 'Ontario', 'g3j 3Y8', '3-515-570-4504', '5-531-547-6964', null, '4', 'Casual ', 'chicksi', 'b', '2014-11-13', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Jason', 'Pierce', '33 Memorial Lane', 'Hamilton', 'Ontario', 'f6i 3q3', '8-795-342-5865', '1-164-434-1476', null, '4', 'Casual ', 'jpiercej', 'i', '2015-12-28', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Kathryn', 'Powell', '447 Morning Circle', 'Orangeville', 'Ontario', 'M1K 4t9', '7-624-281-0680', '5-313-127-1944', null, '3', 'Casual ', 'kpowellk', 'o', '2015-08-07', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Brenda', 'Baker', '301 Talisman Road', 'Ajax', 'Ontario', 'e1V 9C3', '3-442-012-3228', '4-017-135-8359', null, '3', 'Full Time ', 'bbakerl', 'w', '2015-02-12', '2015-12-14');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Rose', 'Little', '0 Transport Point', 'Hamilton', 'Ontario', 'K3z 9b6', '2-866-047-3057', null, null, '5', 'Full Time ', 'rlittlem', 'A', '2015-01-09', '2015-12-07');
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('George', 'Cruz', '0 Gina Center', 'Hamilton', 'Ontario', 'j9f 0h7', '9-685-131-0180', null, null, '3', 'Full Time ', 'gcruzn', 'C', '2014-12-16', null);
insert into Employee (FirstName, LastName, Street, City, State_Province, PostalCode, HomePhone, CellPhone, Email, PositionID, JobType, UserName, Password, HireDate, TerminationDate) values ('Jeremy', 'Mason', '8 Sycamore Center', 'Orangeville', 'Ontario', 'X9X 8H2', '6-213-244-1460', '9-217-912-9031', null, '5', 'Full Time ', 'jmasono', 'P', '2015-08-12', null);

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
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 61,10000018,1, saleprice from product where product.ID = 10000018;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 41,10000009,1, saleprice from product where product.ID = 10000009;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 28,10000006,4, saleprice from product where product.ID = 10000006;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 92,10000014,5, saleprice from product where product.ID = 10000014;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 47,10000017,3, saleprice from product where product.ID = 10000017;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 98,10000012,2, saleprice from product where product.ID = 10000012;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 52,10000022,2, saleprice from product where product.ID = 10000022;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 34,10000020,4, saleprice from product where product.ID = 10000020;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 84,10000010,1, saleprice from product where product.ID = 10000010;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 78,10000003,2, saleprice from product where product.ID = 10000003;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 56,10000015,3, saleprice from product where product.ID = 10000015;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 98,10000022,3, saleprice from product where product.ID = 10000022;
INSERT INTO `TransactionRecord` (`TransactionID`,`ProductID`,`QuantitySold`,`UnitPrice`) Select 55,10000023,3, saleprice from product where product.ID = 10000023;
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

/*------------------- UPDATING TRANSACTION -----------------------*/
UPDATE Transaction t 
SET SubTotal = (SELECT IFNULL(SUM(UnitPrice * QuantitySold), 0) 
			FROM TransactionRecord tr WHERE tr.TransactionId = t.ID);

UPDATE Transaction t 
SET Tax = ROUND(SubTotal * 0.13, 2),
    Total = ROUND(SubTotal + Tax, 2);
    
/*-------------------------- ORDER AND PAYMENT RELATED CONTENT -----------------------------*/
create table Invoice
(
	ID				int				not null	AUTO_INCREMENT,
	AmountDue		double						DEFAULT 0,
	AmountPaid		double						DEFAULT 0,
	ReceivedDate	timestamp		DEFAULT current_timestamp,
	SupplierID		int 			not null,
	
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

DELIMITER $$
CREATE TRIGGER Change_Amount_Due 
	AFTER UPDATE ON `Order`
	FOR EACH ROW
BEGIN
IF NEW.InvoiceID IS NOT NULL THEN
	    UPDATE Invoice
		SET AmountDue = (select Sum(o.Cost) from `Order` o 
						where Invoice.ID = o.InvoiceID),
			AmountPaid = (select Sum(IFNULL(o.AmountPaid, 0)) from `Order` o 
						where Invoice.ID = o.InvoiceID);
END IF;
END $$
DELIMITER ;

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
create table OrderDetail
(

	OrderID				int			not null,
	ProductID			int(8) 		not null,
	OrderedQuantity		int 		not null,
	ReceivedQuantity	int,
	Cost				double,
	
	PRIMARY KEY(OrderID, ProductID),
	FOREIGN KEY (OrderID) REFERENCES `Order`(ID),
	FOREIGN KEY (ProductID) REFERENCES Product(ID),
	CONSTRAINT CK_OrderDetail_Cost check (Cost > 0)
);

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
