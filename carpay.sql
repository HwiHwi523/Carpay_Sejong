DROP DATABASE IF EXISTS carpay;/**/
DROP USER IF EXISTS carpay_mng1@localhost;

create user carpay_mng1@localhost identified with mysql_native_password by 'mng000';
create database carpay;
grant all privileges on carpay.* to 'carpay_mng1'@localhost with grant option;
commit; 

/*DROP TABLE consm_mem; 
DROP TABLE busin_mem; 
DROP TABLE simple_pay; 
DROP TABLE paymentlist;/*리셋용*/ 

/*개인정보, 차량소유주 정보, 사업자번호 테이블*/

/*DROP TABLE personal_info;
DROP TABLE car_info;
DROP TABLE businessperson_info; /*리셋용*/

/*계좌번호 정보, 카드 정보*/

/*DROP TABLE bank_account; 
DROP TABLE card_info; /*리셋용*/ 

USE carpay;


/*기존 소비자 회원 정보, 기존 사업자 회원 정보, 간편결제 정보, 결제이력*/



/*select * from consm_mem; /*기존 소비자 회원*/
/*select * from busin_mem; /*기존 사업자 회원*/
/*select * from simple_pay; /*간편결제 수단 정보 (결제수단 등록 후 저장테이블)*/
/*select * from paymentlist; /*결제이력 테이블*/
/*select * from personal_info; /*개인정보(주민번호, 이름, 핸드폰번호)*/
/*select * from car_info; /*차량정보(차량번호, 주민번호)*/
/*select * from businessperson_info; /*사업자번호 정보(사업자번호, 주민번호)*/
/*select * from bank_account; /*계좌 정보*/
/*select * from card_info; /*카드 정보*/
/*SELECT pm_author, dp_comp, pay_amount, pay_day FROM paymentlist WHERE wd_comp = (SELECT bs_shopn FROM busin_mem WHERE bs_id = "2468aaa" ) AND pay_day >= "2020-07-19" AND pay_day <= "2021-02-04";
*/
CREATE TABLE consm_mem 
(
   con_id CHAR(12) primary key NOT NULL,
    con_pw CHAR(14) NOT NULL,
    con_name VARCHAR(80) NOT NULL,
    con_resinum CHAR(15) NOT NULL,
    con_ph CHAR(13) NOT NULL,
    con_car CHAR(10),
    con_paypw CHAR(4)
); /*기존소비자 회원정보 테이블 생성*/

CREATE TABLE busin_mem 
(
   bs_id CHAR(12) primary key NOT NULL,
    bs_pw CHAR(14) NOT NULL,
    bs_name VARCHAR(80) NOT NULL,
    bs_ph CHAR(13) NOT NULL,
    bs_num CHAR(10) NOT NULL,
   bs_shopn CHAR(45) NOT NULL
); /*기존사업자 회원정보 테이블 생성*/

CREATE TABLE simple_pay  
(   
   pay_num CHAR(20) primary key NOT NULL,
    con_id CHAR(12) NOT NULL,
    comp_name VARCHAR(15) NOT NULL
); /*간편결제정보 테이블 생성*/

CREATE TABLE paymentlist 
(
   pm_author CHAR(8) primary key NOT NULL, /*승인번호*/
    con_id CHAR(12) NOT NULL, /*소비자 id*/
    pay_num CHAR(20) NOT NULL, /*결제수단 번호(카드번호, 계좌번호)*/
    wd_comp CHAR(45) NOT NULL, /*가게명, 거래처*/
    dp_comp CHAR(15) NOT NULL, /*은행 혹은 카드사명, 결제수단*/
    pay_amount CHAR(10) NOT NULL, /*총 결제금액*/
    pay_day CHAR(10) NOT NULL, /*결제 날짜*/
    deal_num CHAR(4) NOT NULL /*거래번호*/
    
); /*결제이력 테이블 생성*/

CREATE TABLE personal_info 
(
   resident_num CHAR(14) primary key NOT NULL,
    per_name VARCHAR(80) NOT NULL,
    phone_num CHAR(13) NOT NULL
); /*개인정보 테이블 생성*/

CREATE TABLE car_info 
(
   car_number CHAR(9) primary key NOT NULL,
    resident_num CHAR(14) NOT NULL
); /*차량 소유주 테이블 생성*/

CREATE TABLE businessperson_info 
(
   busin_num CHAR(10) primary key NOT NULL,
    resident_num CHAR(14) NOT NULL
); /*사업자번호 테이블 생성*/

CREATE TABLE bank_account 
(
   account_num CHAR(20) primary key NOT NULL,
    bank_name VARCHAR(15) NOT NULL,
    resident_num CHAR(14) NOT NULL,
    account_pw CHAR(4) NOT NULL
); /*계좌번호 정보 테이블 생성*/

CREATE TABLE card_info 
(
   card_num CHAR(16) primary key NOT NULL,
    card_name VARCHAR(9) NOT NULL,
    resident_num CHAR(14) NOT NULL,
    crd_expd CHAR(6) NOT NULL,
    crd_scrt CHAR(3) NOT NULL,
    crd_pw CHAR(4) NOT NULL
); /*계좌번호 정보 테이블 생성*/

insert into consm_mem values ("123abc","password123","김준우","990618-1234581","010-1234-5692","37보4355","1111");
insert into consm_mem values ("a1b2c3","password127","김선우","810228-1234582","010-1234-5693","43나7663","2222");
insert into consm_mem values ("aaaaa","password131","김서아","680316-2345679","010-1234-5709","47저8215","3333");
insert into consm_mem values ("abcdefgh","password135","박하윤","610630-2345680","010-1234-5710","05누0480","4444");
insert into consm_mem values ("12345aa","password139","박서윤","721214-2345681","010-1234-5711","20너0513","5555");


insert into busin_mem values ("2468aaa","password145","남지호","010-1234-5678","1234567890","매장1");
insert into busin_mem values ("222kkk","password355","박도윤","010-1234-5681","1234567891","매장2");
insert into busin_mem values ("8hh8","password565","김지안","010-1234-5708","6789012345","매장3");
insert into busin_mem values ("sell3","password775","양시아","010-1234-5719","6789012348","매장4");
insert into busin_mem values ("seller4","password985","김소율","010-1234-5723","6789012349","매장5");


insert into simple_pay values ("4208567892130050","123abc","신한카드");
/*insert into simple_pay values ("7182567938581180","a1b2c3","KB국민카드"); /*결제수단등록 테스트로 사용*/
insert into simple_pay values ("5026568217287900","aaaaa","우리카드");
insert into simple_pay values ("8000568263739100","abcdefgh","카카오뱅크");
insert into simple_pay values ("31233748890357","aaaaa","하나");
/*insert into simple_pay values ("31234099890258","abcdefgh","우리"); /*결제수단등록 테스트로 사용*/
/*insert into simple_pay values ("31233670890379","12345aa","부산"); /*결제수단등록 테스트로 사용*/



insert into personal_info values ("670101-1234567","남지호","010-1234-5678");
insert into personal_info values ("870204-1234568","김서준","010-1234-5679");
insert into personal_info values ("860305-1234569","김하준","010-1234-5680");
insert into personal_info values ("791108-1234570","박도윤","010-1234-5681");
insert into personal_info values ("950402-1234571","최시우","010-1234-5682");
insert into personal_info values ("000505-3234572","박은우","010-1234-5683");
insert into personal_info values ("960606-1234573","최예준","010-1234-5684");
insert into personal_info values ("630702-1234574","이민준","010-1234-5685");
insert into personal_info values ("681207-1234575","이주원","010-1234-5686");
insert into personal_info values ("780508-1234576","서유준","010-1234-5687");
insert into personal_info values ("720807-1234577","박이준","010-1234-5688");
insert into personal_info values ("821004-1234578","민우진","010-1234-5689");
insert into personal_info values ("891231-1234579","김건우","010-1234-5690");
insert into personal_info values ("911124-1234580","최수호","010-1234-5691");
insert into personal_info values ("990618-1234581","김준우","010-1234-5692");
insert into personal_info values ("810228-1234582","김선우","010-1234-5693");
insert into personal_info values ("920920-1234586","박지후","010-1234-5694");
insert into personal_info values ("020301-3234584","박서진","010-1234-5695");
insert into personal_info values ("030414-3234585","이연우","010-1234-5696");
insert into personal_info values ("030802-3234586","김도현","010-1234-5697");
insert into personal_info values ("920920-1234583","이지안","010-1234-5698");
insert into personal_info values ("961120-1234584","김이안","010-1234-5699");
insert into personal_info values ("931010-1234585","김준서","010-1234-5700");
insert into personal_info values ("940420-1234586","옹현우","010-1234-5701");
insert into personal_info values ("840624-1234587","신우주","010-1234-5702");
insert into personal_info values ("860915-1234588","진지훈","010-1234-5703");
insert into personal_info values ("801225-1234589","주시온","010-1234-5704");
insert into personal_info values ("700929-1234590","양지한","010-1234-5705");
insert into personal_info values ("760101-1234591","김민재","010-1234-5706");
insert into personal_info values ("010825-3234592","박지우","010-1234-5707");
insert into personal_info values ("640120-2345678","김지안","010-1234-5708");
insert into personal_info values ("680316-2345679","김서아","010-1234-5709");
insert into personal_info values ("610630-2345680","박하윤","010-1234-5710");
insert into personal_info values ("721214-2345681","박서윤","010-1234-5711");
insert into personal_info values ("700220-2345682","박하린","010-1234-5712");
insert into personal_info values ("791006-2345683","김하은","010-1234-5713");
insert into personal_info values ("800107-2345684","김서연","010-1234-5714");
insert into personal_info values ("841128-2345685","진수아","010-1234-5715");
insert into personal_info values ("880910-2345686","서지우","010-1234-5716");
insert into personal_info values ("890123-2345687","윤지유","010-1234-5717");
insert into personal_info values ("820412-2345688","윤지아","010-1234-5718");
insert into personal_info values ("900129-2345689","양시아","010-1234-5719");
insert into personal_info values ("901122-2345690","최서현","010-1234-5720");
insert into personal_info values ("940606-2345691","최다은","010-1234-5721");
insert into personal_info values ("970307-2345692","최민서","010-1234-5722");
insert into personal_info values ("990707-2345693","김소율","010-1234-5723");
insert into personal_info values ("001231-2345694","강유주","010-1234-5724");
insert into personal_info values ("020826-2345695","강예린","010-1234-5725");
insert into personal_info values ("030505-2345696","김채원","010-1234-5726");
insert into personal_info values ("030531-2345697","최서우","010-1234-5727");


insert into car_info values ("15구1434","670101-1234567");
insert into car_info values ("24로4941","870204-1234568");
insert into car_info values ("09너3211","860305-1234569");
insert into car_info values ("02버0321","791108-1234570");
insert into car_info values ("60주9200","950402-1234571");
insert into car_info values ("34부0611","000505-3234572");
insert into car_info values ("63노0813","960606-1234573");
insert into car_info values ("35노6514","630702-1234574");
insert into car_info values ("20무7623","681207-1234575");
insert into car_info values ("46하4812","780508-1234576");
insert into car_info values ("40가4984","720807-1234577");
insert into car_info values ("02주7732","821004-1234578");
insert into car_info values ("23서2440","891231-1234579");
insert into car_info values ("04러5057","911124-1234580");
insert into car_info values ("37보4355","990618-1234581");
insert into car_info values ("43나7663","810228-1234582");
insert into car_info values ("70보1467","920920-1234583");
insert into car_info values ("08수4259","020301-3234584");
insert into car_info values ("05조5316","030414-3234585");
insert into car_info values ("08거1857","760101-1234591");
insert into car_info values ("27보9935","640120-2345678");
insert into car_info values ("47저8215","680316-2345679");
insert into car_info values ("05누0480","610630-2345680");
insert into car_info values ("20너0513","721214-2345681");
insert into car_info values ("59서5510","700220-2345682");
insert into car_info values ("04부4744","791006-2345683");
insert into car_info values ("26보0765","800107-2345684");
insert into car_info values ("30러0182","841128-2345685");
insert into car_info values ("15누0476","880910-2345686");
insert into car_info values ("04루1327","890123-2345687");
insert into car_info values ("46라3730","820412-2345688");
insert into car_info values ("07리5334","900129-2345689");
insert into car_info values ("06주4617","901122-2345690");
insert into car_info values ("05도2439","940606-2345691");
insert into car_info values ("04나4527","970307-2345692");
insert into car_info values ("05나8106","990707-2345693");
insert into car_info values ("37두1993","001231-2345694");
insert into car_info values ("06모3448","020826-2345695");
insert into car_info values ("41마2517","961120-1234584");
insert into car_info values ("59우3776","931010-1234585");



insert into businessperson_info values ("1234567890","670101-1234567");
insert into businessperson_info values ("1234567891","791108-1234570");
insert into businessperson_info values ("1234567892","960606-1234573");
insert into businessperson_info values ("1234567893","821004-1234578");
insert into businessperson_info values ("1234567894","931010-1234585");
insert into businessperson_info values ("6789012345","640120-2345678");
insert into businessperson_info values ("6789012346","791006-2345683");
insert into businessperson_info values ("6789012347","841128-2345685");
insert into businessperson_info values ("6789012348","900129-2345689");
insert into businessperson_info values ("6789012349","990707-2345693");


insert into bank_account values ("31234567890126","기업","670101-1234567","1224");
insert into bank_account values ("31234528890137","하나","870204-1234568","8482");
insert into bank_account values ("31234489890148","국민","860305-1234569","5740");
insert into bank_account values ("31234450890159","부산","791108-1234570","2998");
insert into bank_account values ("31234411890170","신한","950402-1234571","3256");
insert into bank_account values ("31234372890181","우리","000505-3234572","3514");
insert into bank_account values ("31234333890192","농협","960606-1234573","4772");
insert into bank_account values ("31234294890203","기업","630702-1234574","5230");
insert into bank_account values ("31234255890214","하나","681207-1234575","5288");
insert into bank_account values ("31234216890225","국민","780508-1234576","6546");
insert into bank_account values ("31234177890236","부산","640120-2345678","7804");
insert into bank_account values ("31234138890247","신한","680316-2345679","8062");
insert into bank_account values ("31234099890258","우리","610630-2345680","8320");
insert into bank_account values ("31234060890269","농협","721214-2345681","5578");
insert into bank_account values ("31234021890280","기업","700220-2345682","1036");
insert into bank_account values ("31233982890291","하나","791006-2345683","1194");
insert into bank_account values ("31233943890302","국민","800107-2345684","1352");
insert into bank_account values ("31233904890313","부산","841128-2345685","1610");
insert into bank_account values ("31233865890324","신한","880910-2345686","1868");
insert into bank_account values ("31233826890335","우리","890123-2345687","9126");
insert into bank_account values ("31233787890346","기업","940420-1234586","1384");
insert into bank_account values ("31233748890357","하나","840624-1234587","3642");
insert into bank_account values ("31233709890368","국민","860915-1234588","6090");
insert into bank_account values ("31233670890379","부산","801225-1234589","8158");
insert into bank_account values ("31233631890390","신한","700929-1234590","1756");


insert into card_info values ("1234567845678920","NH농협카드","911124-1234580","202407","288","2020");
insert into card_info values ("4208567892130050","신한카드","990618-1234581","202201","324","2684");
insert into card_info values ("7182567938581180","KB국민카드","810228-1234582","202202","360","3348");
insert into card_info values ("1156567985032300","삼성카드","920920-1234586","202203","396","4012");
insert into card_info values ("3130568031483400","현대카드","020301-3234584","202204","432","4676");
insert into card_info values ("6104568077934600","하나카드","030414-3234585","202205","468","5340");
insert into card_info values ("1078568124385700","롯데카드","760101-1234591","202206","504","6004");
insert into card_info values ("2052568170836800","비씨카드","640120-2345678","202207","540","6668");
insert into card_info values ("5026568217287900","우리카드","680316-2345679","202208","576","7332");
insert into card_info values ("8000568263739100","카카오뱅크","610630-2345680","202209","612","7996");
insert into card_info values ("3974568310190200","NH농협카드","630702-1234574","202210","648","8660");
insert into card_info values ("3948568356641300","신한카드","681207-1234575","202211","684","9324");
insert into card_info values ("6922568403092500","KB국민카드","780508-1234576","202212","720","6044");
insert into card_info values ("9896568449543600","삼성카드","820412-2345688","202407","756","4822");
insert into card_info values ("2870568495994700","현대카드","900129-2345689","202408","792","3600");
insert into card_info values ("5844568542445900","하나카드","901122-2345690","202409","828","2378");
insert into card_info values ("8818568588897000","롯데카드","940606-2345691","202410","864","1156");
insert into card_info values ("1792568635348100","비씨카드","970307-2345692","202411","900","4676");
insert into card_info values ("4766568681799200","우리카드","990707-2345693","202207","936","5340");
insert into card_info values ("7740568728250400","카카오뱅크","001231-2345694","202208","972","6004");
insert into card_info values ("6714568774701500","NH농협카드","020826-2345695","202209","686","6668");
insert into card_info values ("3688568821152600","신한카드","961120-1234584","202210","400","7332");
insert into card_info values ("6662568867603800","KB국민카드","931010-1234585","202211","114","7996");
insert into card_info values ("9636568914054900","삼성카드","920920-1234583","202212","234","8660");
insert into card_info values ("2610568960506000","현대카드","841128-2345685","202407","808","9324");


insert into paymentlist values ("12345678","aaaaa","5026568217287900","매장2","우리카드","8000","2020-03-21","1234");
insert into paymentlist values ("12345679","12345aa","31233670890379","매장5","부산","9000","2020-03-30","1235");
insert into paymentlist values ("12345680","abcdefgh","8000568263739100","매장1","카카오뱅크","2000","2020-04-10","1236");
insert into paymentlist values ("12345681","a1b2c3","7182567938581180","매장1","KB국민카드","4000","2020-04-20","1237");
insert into paymentlist values ("12345682","123abc","4208567892130050","매장3","신한카드","5600","2020-04-30","1238");
insert into paymentlist values ("12345683","aaaaa","5026568217287900","매장4","우리카드","7800","2020-05-10","1239");
insert into paymentlist values ("12345684","123abc","4208567892130050","매장2","신한카드","8990","2020-05-10","1240");
insert into paymentlist values ("12345685","abcdefgh","8000568263739100","매장3","카카오뱅크","12000","2020-05-30","1241");
insert into paymentlist values ("12345686","a1b2c3","7182567938581180","매장5","KB국민카드","110000","2020-06-09","1242");
insert into paymentlist values ("12345687","12345aa","31233670890379","매장2","부산","24000","2020-06-19","1243");
insert into paymentlist values ("12345688","aaaaa","5026568217287900","매장2","우리카드","98750","2020-06-29","1244");
insert into paymentlist values ("12345689","123abc","4208567892130050","매장5","신한카드","87300","2020-07-09","1245");
insert into paymentlist values ("12345690","abcdefgh","8000568263739100","매장1","카카오뱅크","75850","2020-07-19","1246");
insert into paymentlist values ("12345691","a1b2c3","7182567938581180","매장1","KB국민카드","64400","2020-07-29","1247");
insert into paymentlist values ("12345692","12345aa","31233670890379","매장3","부산","52950","2020-08-08","1248");
insert into paymentlist values ("12345693","aaaaa","5026568217287900","매장4","우리카드","41500","2020-08-18","1249");
insert into paymentlist values ("12345694","12345aa","31233670890379","매장2","부산","30050","2020-08-28","1250");
insert into paymentlist values ("12345695","abcdefgh","8000568263739100","매장3","카카오뱅크","6500","2020-09-07","1251");
insert into paymentlist values ("12345696","a1b2c3","7182567938581180","매장5","KB국민카드","3800","2020-09-17","1252");
insert into paymentlist values ("12345697","123abc","4208567892130050","매장2","신한카드","8000","2020-09-27","1253");
insert into paymentlist values ("12345698","aaaaa","5026568217287900","매장2","우리카드","9000","2020-10-07","1254");
insert into paymentlist values ("12345699","123abc","4208567892130050","매장5","신한카드","2000","2020-10-17","1255");
insert into paymentlist values ("12345700","abcdefgh","8000568263739100","매장1","카카오뱅크","4000","2020-10-27","1256");
insert into paymentlist values ("12345701","a1b2c3","7182567938581180","매장1","KB국민카드","5600","2020-11-06","1257");
insert into paymentlist values ("12345702","12345aa","31233670890379","매장3","부산","7800","2020-11-16","1258");
insert into paymentlist values ("12345703","aaaaa","5026568217287900","매장4","우리카드","8990","2020-11-26","1259");
insert into paymentlist values ("12345704","12345aa","31233670890379","매장2","부산","12000","2020-12-06","1260");
insert into paymentlist values ("12345705","abcdefgh","8000568263739100","매장3","카카오뱅크","110000","2020-12-16","1261");
insert into paymentlist values ("12345706","a1b2c3","7182567938581180","매장5","KB국민카드","24000","2020-12-26","1262");
insert into paymentlist values ("12345707","123abc","4208567892130050","매장2","신한카드","98750","2021-01-05","1263");
insert into paymentlist values ("12345708","aaaaa","5026568217287900","매장2","우리카드","87300","2021-01-15","1264");
insert into paymentlist values ("12345709","123abc","4208567892130050","매장5","신한카드","75850","2021-01-25","1265");
insert into paymentlist values ("12345710","abcdefgh","8000568263739100","매장1","카카오뱅크","64400","2021-02-04","1266");
insert into paymentlist values ("12345711","a1b2c3","7182567938581180","매장1","KB국민카드","52950","2021-02-14","1267");
insert into paymentlist values ("12345712","12345aa","31233670890379","매장3","부산","41500","2021-02-24","1268");
insert into paymentlist values ("12345713","aaaaa","5026568217287900","매장4","우리카드","30050","2021-03-06","1269");
insert into paymentlist values ("12345714","12345aa","31233670890379","매장2","부산","6500","2021-03-16","1270");
insert into paymentlist values ("12345715","abcdefgh","8000568263739100","매장3","카카오뱅크","3800","2021-03-26","1271");
insert into paymentlist values ("12345716","a1b2c3","7182567938581180","매장5","KB국민카드","7800","2021-04-03","1272");
insert into paymentlist values ("12345717","123abc","4208567892130050","매장2","신한카드","8990","2021-04-13","1273");