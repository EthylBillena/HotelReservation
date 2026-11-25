CREATE TABLE guest (
    GuestID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    PhoneNumber VARCHAR(20),
    Email VARCHAR(100),
    Address VARCHAR(255),
    IDNumber VARCHAR(50)
) ENGINE=InnoDB;

CREATE TABLE room (
    RoomID INT AUTO_INCREMENT PRIMARY KEY,
    RoomTypeID INT,
    RoomNumber VARCHAR(20),
    FloorNumber INT,
    Status VARCHAR(30),
    PricePerNight DECIMAL(10,2),

    FOREIGN KEY (RoomTypeID)
    REFERENCES roomtype(RoomTypeID)
) ENGINE=InnoDB;

CREATE TABLE payment (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    ReservationID INT,
    StaffID INT NULL,
    PaymentDate DATE,
    Amount DECIMAL(10,2),
    PaymentMethod VARCHAR(30),
    PaymentStatus VARCHAR(30),

    FOREIGN KEY (ReservationID)
    REFERENCES reservation(ReservationID),

    FOREIGN KEY (StaffID)
    REFERENCES staff(StaffID)
) ENGINE=InnoDB;

Project Title: Hotel Reservation Management System Team/Members: Alyshia Natalaray, Frence Laurent
Loraña, J-ann Vergara, Kim Russel Dinero, Selyssa
Palencia, Ethyl Ellane Billena, Delbert Lester Lustan Section: 2B NT Assigned Roles: Leader: Alyshia Natalaray,  Programmer: Alyshia Natalaray, Sellysa Palencia, Power Point Presentation: Jay Ann Vergara, Ethyl Ellane Billena, Frence Laurent Loraña, Documentation: Kim Russel Dinero, Delbert Lester Lustan. Actual Contribution: Leader: Alyshia Natalaray,  Programmer: Alyshia Natalaray, Sellysa Palencia, Power Point Presentation: Jay Ann Vergara, Ethyl Ellane Billena, Frence Laurent Loraña, Documentation: Kim Russel Dinero, Delbert Lester Lustan.

Project Overview: Problem - 
Manual reservation processes can lead to problems such as duplicate bookings, misplaced records, incorrect reservation details, and delays in assisting customers. 

Intended User: Receptionist, Hotel Administrator, Accounting Staff, Database Administrator, and Guests. 

Objectives: Develop a Hotel Reservation Management System that facilitates the recording, monitoring, and management of hotel reservations, room availability, and guest information to improve the efficiency and organization of hotel operations

Features: Create hotelreservationdb for guests, room types, rooms, reservations, and payments. Use primary keys, foreign keys, and constraints to maintain accurate, consistent data. Normalize the database up to Third Normal Form (3NF). Automatically organize and retrieve records through structured relationships and queries. Display room availability, reservation statuses, and generate booking summaries. Provide CRUD functions, SQL queries, and inventory reports.

Scope: •Records and maintains essential guest details, such as name, contact information, and other information required for reservations. •Stores room information, including room numbers, room types, rates, and availability status. •Allows authorized staff to create, view, update, and cancel reservations. •Enables staff to check reservation dates, booking details, and room availability to help prevent scheduling conflicts. •Reservation Records: Maintains organized booking information that can be retrieved when needed. 

Scope: •Limited to Reservation Management •Payment Processing •External Booking Platform Integration •Dependence on Accurate Data •Technical Requirements

Installation and Execution: 1. Download Source Code 2. Open Github 3. Put the Selection name or Title 4. Select Public on Readme 5. Open README. md for setup details. 6. Find the pencil to edit 7. Insert Documents and Documentation Cloning or Downloading: You can clone or download it in zip file Importing the .sql file: If you already have the SQL file: 1. Open MySQL Workbench. 2. Open your SQL Editor. 3. Click File → Open SQL Script. 4. Select your .sql file. 5. Click the lightning bolt (Execute). 6. Wait for the script to finish executing. 

<img width="1385" height="1005" alt="Screenshot (1)" src="https://github.com/user-attachments/assets/4bd6d9f6-d2e2-4981-85d0-b17b563fa4c0" />
1. Table Description, Important Fields, and Keys

A. Guest Table (Primary Key): Stores guest information in one place. Instead of repeatedly entering personal details, the system can identify each guest using their unique GuestID.
Purpose: Stores the personal information of guests who use the hotel reservation system. Field	Data Type	Description GuestID INT(11)	Unique ID of each guest. FullName	VARCHAR(50)	Complete name of the guest. Contact	VARCHAR(20)	Guest's contact number. Email	VARCHAR(20)	Guest's email address.
Address	VARCHAR(100)	Guest's residential address. RegistrationDate	DATE	Date the guest registered.

Important keys:
-Primary Key (PK): GuestID
-No foreign key is shown.

B. Users Table (Primary Key): Stores user account information. It helps identify who is authorized to access and manage the hotel reservation system based on their assigned role.
Purpose: Stores information about the system users or staff members who manage hotel reservations. Field 	Data Type Description UserID 	INT(11)	Unique ID of each user. FullName	VARCHAR(100)	Complete name of the user.Email VARCHAR(100)	User's email address. Password	VARCHAR(255)	User's account password. Role	VARCHAR(20)	Identifies the user's role or access level.

Important keys:
- Primary Key (PK): UserID
- No foreign key is shown.

C. Reservations Table (Primary Key + Foreign Keys): This is the main table of the hotel reservation system. It connects guest information, room information, and user accounts. It also records the check-in date, check-out date, total amount, and reservation status. 
Purpose: Stores the main information about hotel reservations, including guest details, room assignments, check-in and check-out dates, and reservation status. Field	Data Type	Description reservation_id	INT(11)	Unique ID of each reservation.
guest_name	VARCHAR(100)	Name of the guest making the reservation. room_no	VARCHAR(20)	Room number assigned to the guest. check_in	DATE	Guest's check-in date. check_out	DATE	Guest's check-out date. total_amount	DECIMAL(10,2)	Total cost of the reservation. status	VARCHAR(20)	Indicates the current reservation status. GuestID	INT(11)	Identifies the guest making the reservation. room_id	INT(11)	Identifies the reserved room. UserID	INT(11)	Identifies the user who manages the reservation.

Important keys:
- Primary Key (PK): reservation_id
- Foreign Key (FK): GuestID references guest.GuestID.
- Foreign Key (FK): room_id references room.room_id.
- Foreign Key (FK): UserID references users.UserID.

D. Payment Table (Primary Key + Foreign Key): This table keeps track of guest payments. It records the payment date, amount, payment method, and payment status. It also connects each payment to its corresponding reservation through ReservationID. 
Purpose: Stores information about payments made for hotel reservations. Field	Data Type	Description PaymentID	INT(11)	Unique ID of each payment. ReservationID	INT(11)	Identifies the reservation associated with the payment. PaymentDate	DATE	Date the payment was made. Amount	DECIMAL(10,2)	Amount paid by the guest. PaymentMethod	VARCHAR(20)	Method used to pay for the reservation. status	VARCHAR(20)	Indicates the current payment status.

Important keys:
- Primary Key (PK): PaymentID
- Foreign Key (FK): ReservationID references reservations.reservation_id.

E. Room Table (Primary Key): This table stores the available room information in the hotel. It helps the system identify rooms and monitor their current status, making it easier to manage room reservations.
Purpose: Stores information about the hotel rooms, including their room numbers and availability status. Field	Data Type	Description
room_id	INT(11)	Unique ID of each room. room_no VARCHAR(20)	Room number assigned to the room. status	VARCHAR(20)	Indicates the current room status.

Important keys:
- Primary Key (PK): room_id
- No foreign key is shown

Summary
Our database consists of five tables: Guest, Users, Reservations, Payment, and Room. Each table has its own purpose in managing the hotel reservation system.

The Guest table stores guest information, while the Users table manages system accounts. The Reservations table serves as the main table because it connects guests, users, and rooms. Meanwhile, the Payment table records the financial transactions associated with reservations, and the Room table stores room information and availability.

We use primary keys (PK) to uniquely identify each record and foreign keys (FK) to establish relationships between tables. These tables work together to make hotel reservations, room management, and payment tracking more organized and efficient.

<img width="1182" height="1141" alt="Screenshot (3)" src="https://github.com/user-attachments/assets/2ced1d76-0b3a-4765-8a19-8339a34c45c3" />
<img width="1215" height="1144" alt="Screenshot (4)" src="https://github.com/user-attachments/assets/6d7a7573-e27c-45b2-a2dc-0fb8e4edb499" />
Image of Create// Add a New Guest
<img width="1208" height="1149" alt="Screenshot (5)" src="https://github.com/user-attachments/assets/cb531eab-e8e3-4a03-aaf9-be4fe75a4754" />
Image of Read// Read Customer Information
<img width="1195" height="1140" alt="Screenshot (7)" src="https://github.com/user-attachments/assets/138051fe-35e9-4203-a1f1-9043fa758f61" />
Image for Update// Update Guest Information/Reservation
<img width="1211" height="1127" alt="Screenshot (8)" src="https://github.com/user-attachments/assets/4a9c37b7-63c2-4c88-a887-0d30a952778c" />
Image of Delete// Delete Guest
<img width="1202" height="1139" alt="Screenshot (6)" src="https://github.com/user-attachments/assets/69146ed4-e44c-43df-bdb5-0e3d63f01627" />
Image of Error// it will be error if they didn't complete the given informations

