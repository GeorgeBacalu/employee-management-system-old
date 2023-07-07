package com.project.ems.mock;

import com.project.ems.user.User;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMock {

    public static List<User> getMockedUsers() {
        return List.of(
              getMockedUser1(), getMockedUser2(), getMockedUser3(), getMockedUser4(), getMockedUser5(), getMockedUser6(), getMockedUser7(), getMockedUser8(), getMockedUser9(),
              getMockedUser10(), getMockedUser11(), getMockedUser12(), getMockedUser13(), getMockedUser14(), getMockedUser15(), getMockedUser16(), getMockedUser17(), getMockedUser18(),
              getMockedUser19(), getMockedUser20(), getMockedUser21(), getMockedUser22(), getMockedUser23(), getMockedUser24(), getMockedUser25(), getMockedUser26(), getMockedUser27(),
              getMockedUser28(), getMockedUser29(), getMockedUser30(), getMockedUser31(), getMockedUser32(), getMockedUser33(), getMockedUser34(), getMockedUser35(), getMockedUser36(),
              getMockedUser37(), getMockedUser38(), getMockedUser39(), getMockedUser40(), getMockedUser41(), getMockedUser42(), getMockedUser43(), getMockedUser44(), getMockedUser45(),
              getMockedUser46(), getMockedUser47(), getMockedUser48(), getMockedUser49(), getMockedUser50(), getMockedUser51(), getMockedUser52(), getMockedUser53(), getMockedUser54(),
              getMockedUser55(), getMockedUser56(), getMockedUser57(), getMockedUser58(), getMockedUser59(), getMockedUser60(), getMockedUser61(), getMockedUser62(), getMockedUser63(),
              getMockedUser64(), getMockedUser65(), getMockedUser66(), getMockedUser67(), getMockedUser68(), getMockedUser69(), getMockedUser70(), getMockedUser71(), getMockedUser72());
    }

    public static List<User> getMockedFilteredUsers() {
        return List.of(getMockedUser1(), getMockedUser2(), getMockedUser3(), getMockedUser4());
    }

    public static User getMockedUser1() {
        return new User(1, "John Doe", "john.doe@example.com", "#John_Doe_Password0", "+40721543701", "123 Main St, Boston, USA", LocalDate.of(1980, 2, 15), getMockedRole2());
    }

    public static User getMockedUser2() {
        return new User(2, "Jane Smith", "jane.smith@example.com", "#Jane_Smith_Password0", "+40756321802", "456 Oak St, London, UK", LocalDate.of(1982, 7, 10), getMockedRole2());
    }

    public static User getMockedUser3() {
        return new User(3, "Michael Johnson", "michael.johnson@example.com", "#Michael_Johnson_Password0", "+40789712303", "789 Pine St, Madrid, Spain", LocalDate.of(1990, 11, 20), getMockedRole2());
    }

    public static User getMockedUser4() {
        return new User(4, "Laura Brown", "laura.brown@example.com", "#Laura_Brown_Password0", "+40734289604", "333 Elm St, Paris, France", LocalDate.of(1985, 8, 25), getMockedRole2());
    }

    public static User getMockedUser5() {
        return new User(5, "Robert Davis", "robert.davis@example.com", "#Robert_Davis_Password0", "+40754321805", "555 Oak St, Berlin, Germany", LocalDate.of(1988, 5, 12), getMockedRole1());
    }

    public static User getMockedUser6() {
        return new User(6, "Emily Wilson", "emily.wilson@example.com", "#Emily_Wilson_Password0", "+40789012606", "777 Pine St, Sydney, Australia", LocalDate.of(1995, 9, 8), getMockedRole1());
    }

    public static User getMockedUser7() {
        return new User(7, "Michaela Taylor", "michaela.taylor@example.com", "#Michaela_Taylor_Password0", "+40723145607", "999 Elm St, Rome, Italy", LocalDate.of(1983, 12, 7), getMockedRole1());
    }

    public static User getMockedUser8() {
        return new User(8, "David Anderson", "david.anderson@example.com", "#David_Anderson_Password0", "+40787654308", "111 Oak St, Moscow, Russia", LocalDate.of(1992, 4, 23), getMockedRole1());
    }

    public static User getMockedUser9() {
        return new User(9, "Sophia Garcia", "sophia.garcia@example.com", "#Sophia_Garcia_Password0", "+40754321809", "333 Pine St, Athens, Greece", LocalDate.of(1998, 7, 30), getMockedRole1());
    }

    public static User getMockedUser10() {
        return new User(10, "Joseph Wilson", "joseph.wilson@example.com", "#Joseph_Wilson_Password0", "+40789012610", "555 Elm St, Madrid, Spain", LocalDate.of(1991, 3, 14), getMockedRole1());
    }

    public static User getMockedUser11() {
        return new User(11, "Olivia Martinez", "olivia.martinez@example.com", "#Olivia_Martinez_Password0", "+40723145611", "777 Oak St, Tokyo, Japan", LocalDate.of(1999, 10, 17), getMockedRole1());
    }

    public static User getMockedUser12() {
        return new User(12, "Daniel Thompson", "daniel.thompson@example.com", "#Daniel_Thompson_Password0", "+40787654312", "999 Elm St, Seoul, South Korea", LocalDate.of(1994, 6, 9), getMockedRole1());
    }

    public static User getMockedUser13() {
        return new User(13, "Emma Thompson", "emma.thompson@example.com", "#Emma_Thompson_Password0", "+40754321813", "111 Pine St, Beijing, China", LocalDate.of(2000, 12, 22), getMockedRole1());
    }

    public static User getMockedUser14() {
        return new User(14, "Liam Brown", "liam.brown@example.com", "#Liam_Brown_Password0", "+40789012614", "333 Oak St, Cape Town, South Africa", LocalDate.of(1997, 9, 4), getMockedRole1());
    }

    public static User getMockedUser15() {
        return new User(15, "Olivia Wilson", "olivia.wilson@example.com", "#Olivia_Wilson_Password0", "+40723145615", "555 Elm St, Buenos Aires, Argentina", LocalDate.of(2001, 4, 7), getMockedRole1());
    }

    public static User getMockedUser16() {
        return new User(16, "Noah Taylor", "noah.taylor@example.com", "#Noah_Taylor_Password0", "+40787654316", "777 Pine St, Rio de Janeiro, Brazil", LocalDate.of(1996, 11, 19), getMockedRole1());
    }

    public static User getMockedUser17() {
        return new User(17, "Ava Johnson", "ava.johnson@example.com", "#Ava_Johnson_Password0", "+40754321817", "999 Oak St, Mexico City, Mexico", LocalDate.of(2002, 6, 2), getMockedRole1());
    }

    public static User getMockedUser18() {
        return new User(18, "William Davis", "william.davis@example.com", "#William_Davis_Password0", "+40789012618", "111 Elm St, Vancouver, Canada", LocalDate.of(1993, 2, 25), getMockedRole1());
    }

    public static User getMockedUser19() {
        return new User(19, "Sophia Martinez", "sophia.martinez@example.com", "#Sophia_Martinez_Password0", "+40723145619", "333 Oak St, Paris, France", LocalDate.of(2003, 9, 8), getMockedRole1());
    }

    public static User getMockedUser20() {
        return new User(20, "Isabella Anderson", "isabella.anderson@example.com", "#Isabella_Anderson_Password0", "+40787654320", "555 Elm St, London, UK", LocalDate.of(1999, 6, 22), getMockedRole1());
    }

    public static User getMockedUser21() {
        return new User(21, "Mason Thompson", "mason.thompson@example.com", "#Mason_Thompson_Password0", "+40754321821", "777 Pine St, Berlin, Germany", LocalDate.of(2002, 12, 20), getMockedRole1());
    }

    public static User getMockedUser22() {
        return new User(22, "Charlotte Thompson", "charlotte.thompson@example.com", "#Charlotte_Thompson_Password0", "+40789012622", "999 Oak St, Moscow, Russia", LocalDate.of(1998, 10, 18), getMockedRole1());
    }

    public static User getMockedUser23() {
        return new User(23, "Elijah Smith", "elijah.smith@example.com", "#Elijah_Smith_Password0", "+40723145623", "111 Elm St, Athens, Greece", LocalDate.of(2003, 5, 3), getMockedRole1());
    }

    public static User getMockedUser24() {
        return new User(24, "Amelia Johnson", "amelia.johnson@example.com", "#Amelia_Johnson_Password0", "+40787654324", "333 Pine St, Madrid, Spain", LocalDate.of(1998, 12, 14), getMockedRole1());
    }

    public static User getMockedUser25() {
        return new User(25, "Harper Wilson", "harper.wilson@example.com", "#Harper_Wilson_Password0", "+40754321825", "555 Oak St, Tokyo, Japan", LocalDate.of(2001, 7, 27), getMockedRole1());
    }

    public static User getMockedUser26() {
        return new User(26, "Daniel Thompson", "daniel.thompson@example.com", "#Daniel_Thompson_Password0", "+40789012626", "777 Elm St, Seoul, South Korea", LocalDate.of(2001, 2, 9), getMockedRole1());
    }

    public static User getMockedUser27() {
        return new User(27, "Liam Thompson", "liam.thompson@example.com", "#Liam_Thompson_Password0", "+40723145627", "999 Oak St, Beijing, China", LocalDate.of(2000, 9, 23), getMockedRole1());
    }

    public static User getMockedUser28() {
        return new User(28, "Grace Martinez", "grace.martinez@example.com", "#Grace_Martinez_Password0", "+40787654328", "111 Elm St, Cape Town, South Africa", LocalDate.of(2002, 6, 6), getMockedRole1());
    }

    public static User getMockedUser29() {
        return new User(29, "Isabella White", "isabella.white@example.com", "#Isabella_White_Password0", "+40754321829", "333 Pine St, Buenos Aires, Argentina", LocalDate.of(2002, 1, 19), getMockedRole1());
    }

    public static User getMockedUser30() {
        return new User(30, "Logan Thompson", "logan.thompson@example.com", "#Logan_Thompson_Password0", "+40789012630", "555 Elm St, Rio de Janeiro, Brazil", LocalDate.of(2003, 7, 2), getMockedRole1());
    }

    public static User getMockedUser31() {
        return new User(31, "Evelyn Brown", "evelyn.brown@example.com", "#Evelyn_Brown_Password0", "+40723145631", "777 Oak St, Mexico City, Mexico", LocalDate.of(1999, 3, 16), getMockedRole1());
    }

    public static User getMockedUser32() {
        return new User(32, "Henry Davis", "henry.davis@example.com", "#Henry_Davis_Password0", "+40787654332", "999 Elm St, Vancouver, Canada", LocalDate.of(1998, 10, 29), getMockedRole1());
    }

    public static User getMockedUser33() {
        return new User(33, "Sofia Smith", "sofia.smith@example.com", "#Sofia_Smith_Password0", "+40754321833", "111 Oak St, Paris, France", LocalDate.of(2000, 6, 11), getMockedRole1());
    }

    public static User getMockedUser34() {
        return new User(34, "Jack Wilson", "jack.wilson@example.com", "#Jack_Wilson_Password0", "+40789012634", "333 Elm St, London, UK", LocalDate.of(1997, 1, 24), getMockedRole1());
    }

    public static User getMockedUser35() {
        return new User(35, "Emily Anderson", "emily.anderson@example.com", "#Emily_Anderson_Password0", "+40723145635", "555 Pine St, Berlin, Germany", LocalDate.of(1995, 8, 6), getMockedRole1());
    }

    public static User getMockedUser36() {
        return new User(36, "Benjamin Thompson", "benjamin.thompson@example.com", "#Benjamin_Thompson_Password0", "+40787654336", "777 Elm St, Moscow, Russia", LocalDate.of(1996, 3, 20), getMockedRole1());
    }

    public static User getMockedUser37() {
        return new User(37, "Abigail Johnson", "abigail.johnson@example.com", "#Abigail_Johnson_Password0", "+40754321837", "999 Oak St, Athens, Greece", LocalDate.of(2000, 10, 2), getMockedRole1());
    }

    public static User getMockedUser38() {
        return new User(38, "Michael Davis", "michael.davis@example.com", "#Michael_Davis_Password0", "+40789012638", "111 Oak St, Madrid, Spain", LocalDate.of(1994, 5, 16), getMockedRole1());
    }

    public static User getMockedUser39() {
        return new User(39, "Mia Wilson", "mia.wilson@example.com", "#Mia_Wilson_Password0", "+40723145639", "333 Elm St, Tokyo, Japan", LocalDate.of(1990, 12, 29), getMockedRole1());
    }

    public static User getMockedUser40() {
        return new User(40, "James Lee", "james.lee@example.com", "#James_Lee_Password0", "+40787654340", "555 Pine St, Seoul, South Korea", LocalDate.of(1991, 8, 11), getMockedRole1());
    }

    public static User getMockedUser41() {
        return new User(41, "Charlotte Thompson", "charlotte.thompson@example.com", "#Charlotte_Thompson_Password0", "+40754321841", "777 Elm St, Beijing, China", LocalDate.of(1993, 3, 24), getMockedRole1());
    }

    public static User getMockedUser42() {
        return new User(42, "Ethan Smith", "ethan.smith@example.com", "#Ethan_Smith_Password0", "+40789012642", "999 Oak St, Cape Town, South Africa", LocalDate.of(1989, 11, 6), getMockedRole1());
    }

    public static User getMockedUser43() {
        return new User(43, "Amelia Johnson", "amelia.johnson@example.com", "#Amelia_Johnson_Password0", "+40723145643", "111 Elm St, Buenos Aires, Argentina", LocalDate.of(1994, 6, 19), getMockedRole1());
    }

    public static User getMockedUser44() {
        return new User(44, "Emily Davis", "emily.davis@example.com", "#Emily_Davis_Password0", "+40787654344", "333 Elm St, Rio de Janeiro, Brazil", LocalDate.of(1998, 1, 1), getMockedRole1());
    }

    public static User getMockedUser45() {
        return new User(45, "Henry Wilson", "henry.wilson@example.com", "#Henry_Wilson_Password0", "+40754321845", "555 Pine St, Mexico City, Mexico", LocalDate.of(2001, 8, 14), getMockedRole1());
    }

    public static User getMockedUser46() {
        return new User(46, "Scarlett Thompson", "scarlett.thompson@example.com", "#Scarlett_Thompson_Password0", "+40789012646", "777 Elm St, Vancouver, Canada", LocalDate.of(2002, 3, 28), getMockedRole1());
    }

    public static User getMockedUser47() {
        return new User(47, "Jacob Brown", "jacob.brown@example.com", "#Jacob_Brown_Password0", "+40723145647", "999 Pine St, Paris, France", LocalDate.of(1999, 11, 10), getMockedRole1());
    }

    public static User getMockedUser48() {
        return new User(48, "Ava Smith", "ava.smith@example.com", "#Ava_Smith_Password0", "+40787654348", "111 Pine St, London, UK", LocalDate.of(1986, 6, 23), getMockedRole1());
    }

    public static User getMockedUser49() {
        return new User(49, "Oliver Johnson", "oliver.johnson@example.com", "#Oliver_Johnson_Password0", "+40754321849", "333 Elm St, Berlin, Germany", LocalDate.of(1988, 2, 5), getMockedRole1());
    }

    public static User getMockedUser50() {
        return new User(50, "Sophia Wilson", "sophia.wilson@example.com", "#Sophia_Wilson_Password0", "+40789012650", "555 Elm St, Moscow, Russia", LocalDate.of(1994, 9, 19), getMockedRole1());
    }

    public static User getMockedUser51() {
        return new User(51, "William Davis", "william.davis@example.com", "#William_Davis_Password0", "+40723145651", "777 Pine St, Athens, Greece", LocalDate.of(1996, 4, 3), getMockedRole1());
    }

    public static User getMockedUser52() {
        return new User(52, "Mia Johnson", "mia.johnson@example.com", "#Mia_Johnson_Password0", "+40787654352", "999 Oak St, Madrid, Spain", LocalDate.of(1998, 11, 16), getMockedRole1());
    }

    public static User getMockedUser53() {
        return new User(53, "James Lee", "james.lee@example.com", "#James_Lee_Password0", "+40754321853", "111 Elm St, Tokyo, Japan", LocalDate.of(1997, 6, 29), getMockedRole1());
    }

    public static User getMockedUser54() {
        return new User(54, "Charlotte Brown", "charlotte.brown@example.com", "#Charlotte_Brown_Password0", "+40789012654", "333 Pine St, Seoul, South Korea", LocalDate.of(2000, 2, 12), getMockedRole1());
    }

    public static User getMockedUser55() {
        return new User(55, "Ethan Smith", "ethan.smith@example.com", "#Ethan_Smith_Password0", "+40723145655", "555 Elm St, Beijing, China", LocalDate.of(1998, 9, 25), getMockedRole1());
    }

    public static User getMockedUser56() {
        return new User(56, "Emily Davis", "emily.davis@example.com", "#Emily_Davis_Password0", "+40787654356", "777 Elm St, Cape Town, South Africa", LocalDate.of(2001, 7, 9), getMockedRole1());
    }

    public static User getMockedUser57() {
        return new User(57, "Jacob Thompson", "jacob.thompson@example.com", "#Jacob_Thompson_Password0", "+40754321857", "999 Pine St, Buenos Aires, Argentina", LocalDate.of(1996, 2, 22), getMockedRole1());
    }

    public static User getMockedUser58() {
        return new User(58, "Sophia Wilson", "sophia.wilson@example.com", "#Sophia_Wilson_Password0", "+40789012658", "111 Elm St, Rio de Janeiro, Brazil", LocalDate.of(1995, 10, 7), getMockedRole1());
    }

    public static User getMockedUser59() {
        return new User(59, "Oliver Johnson", "oliver.johnson@example.com", "#Oliver_Johnson_Password0", "+40723145659", "333 Elm St, Mexico City, Mexico", LocalDate.of(1998, 7, 21), getMockedRole1());
    }

    public static User getMockedUser60() {
        return new User(60, "Scarlett Wilson", "scarlett.wilson@example.com", "#Scarlett_Wilson_Password0", "+40787654360", "555 Elm St, Vancouver, Canada", LocalDate.of(2001, 5, 5), getMockedRole1());
    }

    public static User getMockedUser61() {
        return new User(61, "William Davis", "william.davis@example.com", "#William_Davis_Password0", "+40754321861", "777 Pine St, Paris, France", LocalDate.of(1999, 12, 17), getMockedRole1());
    }

    public static User getMockedUser62() {
        return new User(62, "Mia Johnson", "mia.johnson@example.com", "#Mia_Johnson_Password0", "+40789012662", "999 Oak St, London, UK", LocalDate.of(2002, 9, 30), getMockedRole1());
    }

    public static User getMockedUser63() {
        return new User(63, "James Lee", "james.lee@example.com", "#James_Lee_Password0", "+40723145663", "111 Elm St, Berlin, Germany", LocalDate.of(1996, 5, 14), getMockedRole1());
    }

    public static User getMockedUser64() {
        return new User(64, "Charlotte Brown", "charlotte.brown@example.com", "#Charlotte_Brown_Password0", "+40787654364", "333 Elm St, Moscow, Russia", LocalDate.of(2002, 12, 27), getMockedRole1());
    }

    public static User getMockedUser65() {
        return new User(65, "Elijah Roberts", "elijah.roberts@example.com", "#Elijah_Roberts_Password0", "+40754321865", "555 Pine St, Sydney, Australia", LocalDate.of(2003, 1, 27), getMockedRole1());
    }

    public static User getMockedUser66() {
        return new User(66, "Amelia Walker", "amelia.walker@example.com", "#Amelia_Walker_Password0", "+40789012666", "777 Oak St, Rome, Italy", LocalDate.of(1992, 8, 12), getMockedRole1());
    }

    public static User getMockedUser67() {
        return new User(67, "Daniel Green", "daniel.green@example.com", "#Daniel_Green_Password0", "+40723145667", "999 Elm St, Moscow, Russia", LocalDate.of(1996, 3, 27), getMockedRole1());
    }

    public static User getMockedUser68() {
        return new User(68, "Liam Hall", "liam.hall@example.com", "#Liam_Hall_Password0", "+40787654368", "111 Oak St, Athens, Greece", LocalDate.of(1998, 11, 9), getMockedRole1());
    }

    public static User getMockedUser69() {
        return new User(69, "Sophia Young", "sophia.young@example.com", "#Sophia_Young_Password0", "+40754321869", "333 Pine St, Madrid, Spain", LocalDate.of(1995, 6, 23), getMockedRole1());
    }

    public static User getMockedUser70() {
        return new User(70, "Noah Clark", "noah.clark@example.com", "#Noah_Clark_Password0", "+40789012670", "555 Elm St, Tokyo, Japan", LocalDate.of(1997, 2, 5), getMockedRole1());
    }

    public static User getMockedUser71() {
        return new User(71, "Olivia Hill", "olivia.hill@example.com", "#Olivia_Hill_Password0", "+40723145671", "777 Oak St, Seoul, South Korea", LocalDate.of(2001, 9, 20), getMockedRole1());
    }

    public static User getMockedUser72() {
        return new User(72, "Michaela Allen", "michaela.allen@example.com", "#Michaela_Allen_Password0", "+40787654372", "999 Pine St, Beijing, China", LocalDate.of(1998, 5, 6), getMockedRole1());
    }
}
