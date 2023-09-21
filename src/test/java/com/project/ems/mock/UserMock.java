package com.project.ems.mock;

import com.project.ems.user.User;
import com.project.ems.user.UserDto;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.AuthorityMock.getMockedAuthoritiesIds;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMock {

    public static List<User> getMockedUsers() {
        return List.of(getMockedUser1(), getMockedUser2(), getMockedUser3(), getMockedUser4(), getMockedUser5(), getMockedUser6(), getMockedUser7(), getMockedUser8(), getMockedUser9(),
              getMockedUser10(), getMockedUser11(), getMockedUser12(), getMockedUser13(), getMockedUser14(), getMockedUser15(), getMockedUser16(), getMockedUser17(), getMockedUser18(),
              getMockedUser19(), getMockedUser20(), getMockedUser21(), getMockedUser22(), getMockedUser23(), getMockedUser24(), getMockedUser25(), getMockedUser26(), getMockedUser27(),
              getMockedUser28(), getMockedUser29(), getMockedUser30(), getMockedUser31(), getMockedUser32(), getMockedUser33(), getMockedUser34(), getMockedUser35(), getMockedUser36(),
              getMockedUser37(), getMockedUser38(), getMockedUser39(), getMockedUser40(), getMockedUser41(), getMockedUser42(), getMockedUser43(), getMockedUser44(), getMockedUser45(),
              getMockedUser46(), getMockedUser47(), getMockedUser48(), getMockedUser49(), getMockedUser50(), getMockedUser51(), getMockedUser52(), getMockedUser53(), getMockedUser54(),
              getMockedUser55(), getMockedUser56(), getMockedUser57(), getMockedUser58(), getMockedUser59(), getMockedUser60(), getMockedUser61(), getMockedUser62(), getMockedUser63(),
              getMockedUser64(), getMockedUser65(), getMockedUser66(), getMockedUser67(), getMockedUser68(), getMockedUser69(), getMockedUser70(), getMockedUser71(), getMockedUser72());
    }

    public static List<User> getMockedUsersPage1() {
        return List.of(getMockedUser1(), getMockedUser2());
    }

    public static List<User> getMockedUsersPage2() {
        return List.of(getMockedUser3(), getMockedUser4());
    }

    public static List<User> getMockedUsersPage3() {
        return List.of(getMockedUser5(), getMockedUser6());
    }

    public static List<UserDto> getMockedUserDtosPage1() {
        return List.of(getMockedUserDto1(), getMockedUserDto2());
    }

    public static List<UserDto> getMockedUserDtosPage2() {
        return List.of(getMockedUserDto3(), getMockedUserDto4());
    }

    public static List<UserDto> getMockedUserDtosPage3() {
        return List.of(getMockedUserDto5(), getMockedUserDto6());
    }

    public static List<User> getMockedUsersFirstPage() { return List.of(getMockedUser1(), getMockedUser2(), getMockedUser3(), getMockedUser4(), getMockedUser5(), getMockedUser6(), getMockedUser7(), getMockedUser8(), getMockedUser9(), getMockedUser10()); }

    public static List<UserDto> getMockedUserDtosFirstPage() { return List.of(getMockedUserDto1(), getMockedUserDto2(), getMockedUserDto3(), getMockedUserDto4(), getMockedUserDto5(), getMockedUserDto6(), getMockedUserDto7(), getMockedUserDto8(), getMockedUserDto9(), getMockedUserDto10()); }

    public static User getMockedUser1() { return User.builder().id(1).name("John Doe").email("john.doe@example.com").password("$2a$10$2cEzAx1UKlGGlWai6VasvOUdVBg.NCug8XsdOyhwRdvLYYgFW.O/W").mobile("+40721543701").address("123 Main St, Boston, USA").birthday(LocalDate.of(1980, 2, 15)).role(getMockedRole2()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser2() { return User.builder().id(2).name("Jane Smith").email("jane.smith@example.com").password("$2a$10$tltXH0bchhhvzf6sWpZOduMfTdNFzORnJjo2quB9e8qTFttHj8RsG").mobile("+40756321802").address("456 Oak St, London, UK").birthday(LocalDate.of(1982, 7, 10)).role(getMockedRole2()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser3() { return User.builder().id(3).name("Michael Johnson").email("michael.johnson@example.com").password("$2a$10$IeJe8nsYE4MCiuIXTEk7uetLN/T5ynAKtApxJwVMBmDC6Bk3W3W3y").mobile("+40789712303").address("789 Pine St, Madrid, Spain").birthday(LocalDate.of(1990, 11, 20)).role(getMockedRole2()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser4() { return User.builder().id(4).name("Laura Brown").email("laura.brown@example.com").password("$2a$10$OvqxdzK0hef5A.jcW3PQOewRrZnafHtpAiAFn.aF8KW1ktaRvb5NO").mobile("+40734289604").address("333 Elm St, Paris, France").birthday(LocalDate.of(1985, 8, 25)).role(getMockedRole2()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser5() { return User.builder().id(5).name("Robert Davis").email("robert.davis@example.com").password("$2a$10$vpnwsCU8bOzYq2l8CljiBeOcZZRfVjeo4g96XnH2KYbqNRQYfZQh.").mobile("+40754321805").address("555 Oak St, Berlin, Germany").birthday(LocalDate.of(1988, 5, 12)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser6() { return User.builder().id(6).name("Emily Wilson").email("emily.wilson@example.com").password("$2a$10$GNYbCsERVLU1Vw7L9obxAOX9spb8QgGSc8SSzAfQwGVIE/UMZ.ItK").mobile("+40789012606").address("777 Pine St, Sydney, Australia").birthday(LocalDate.of(1995, 9, 8)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser7() { return User.builder().id(7).name("Michaela Taylor").email("michaela.taylor@example.com").password("$2a$10$ZRHHzDuEirPWk/GA22HQ1u/Rm6sNV4VPO9WhxYfgKSuCvF73AMIO6").mobile("+40723145607").address("999 Elm St, Rome, Italy").birthday(LocalDate.of(1983, 12, 7)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser8() { return User.builder().id(8).name("David Anderson").email("david.anderson@example.com").password("$2a$10$A8YW129edU79eHirdlT.2u4cQQy5bCgJIBie2YrDyYfoUSi4i5/GO").mobile("+40787654308").address("111 Oak St, Moscow, Russia").birthday(LocalDate.of(1992, 4, 23)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser9() { return User.builder().id(9).name("Sophia Garcia").email("sophia.garcia@example.com").password("$2a$10$uO2oGV6PM5aBrAmqR98LQu2FtC/iaFoXx2nsyNXuOhNbaEOzXKbx2").mobile("+40754321809").address("333 Pine St, Athens, Greece").birthday(LocalDate.of(1998, 7, 30)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser10() { return User.builder().id(10).name("Joseph Wilson").email("joseph.wilson@example.com").password("$2a$10$aoNj5vm.RdmMDffhpCgpu.mCmhgbanqS1R1CHeFR1iSjv/cASQa4W").mobile("+40789012610").address("555 Elm St, Madrid, Spain").birthday(LocalDate.of(1991, 3, 14)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser11() { return User.builder().id(11).name("Olivia Martinez").email("olivia.martinez@example.com").password("$2a$10$Gq9twxRqP68mHkvNCJIPt.Gh0Jhb28SrbBIczDhiBOcVCvH1VHC3C").mobile("+40723145611").address("777 Oak St, Tokyo, Japan").birthday(LocalDate.of(1999, 10, 17)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser12() { return User.builder().id(12).name("Daniel Thompson").email("daniel.thompson@example.com").password("$2a$10$oskNRX/aOXL5UunrCXbIkeJI3AKhmwNJENNNnj9/YXsofKQLVSTbW").mobile("+40787654312").address("999 Elm St, Seoul, South Korea").birthday(LocalDate.of(1994, 6, 9)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser13() { return User.builder().id(13).name("Emma Thompson").email("emma.thompson@example.com").password("$2a$10$WsezewgzAH68WmkfpXJuF..k4FxuZb5ZDsNfdPC4.VXCPc2p4Y95m").mobile("+40754321813").address("111 Pine St, Beijing, China").birthday(LocalDate.of(2000, 12, 22)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser14() { return User.builder().id(14).name("Liam Brown").email("liam.brown@example.com").password("$2a$10$xeXGbNuNk/3KcL826T4d8.08P4VXykQvwZ0YQRuVIlmJU49Lv2vGy").mobile("+40789012614").address("333 Oak St, Cape Town, South Africa").birthday(LocalDate.of(1997, 9, 4)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser15() { return User.builder().id(15).name("Olivia Wilson").email("olivia.wilson@example.com").password("$2a$10$w7EDq9QbQziqsW6RX6t3iO.KnCJnaE.lsjsnKWMDhs9lwqiBQ4rXC").mobile("+40723145615").address("555 Elm St, Buenos Aires, Argentina").birthday(LocalDate.of(2001, 4, 7)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser16() { return User.builder().id(16).name("Noah Taylor").email("noah.taylor@example.com").password("$2a$10$6yV39WUU4bzgOsrN75xueutjZ4ThW374fIzcEb6QmexZlpAbJP4Xq").mobile("+40787654316").address("777 Pine St, Rio de Janeiro, Brazil").birthday(LocalDate.of(1996, 11, 19)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser17() { return User.builder().id(17).name("Ava Johnson").email("ava.johnson@example.com").password("$2a$10$3O3sqTFrFnsrBvBNxgNtMub6j0SzvUvEdTnlA5hPaJkerYS.YUgzG").mobile("+40754321817").address("999 Oak St, Mexico City, Mexico").birthday(LocalDate.of(2002, 6, 2)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser18() { return User.builder().id(18).name("William Davis").email("william.davis@example.com").password("$2a$10$yikPyGgUSjfBRGRa/m2i5O8kKvJue0Fk1aeVzpGUR10hhGbaCpPai").mobile("+40789012618").address("111 Elm St, Vancouver, Canada").birthday(LocalDate.of(1993, 2, 25)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser19() { return User.builder().id(19).name("Sophia Martinez").email("sophia.martinez@example.com").password("$2a$10$/AdEGcsi9Nf3OTEFhTDoGOmRfQz/FcdxnxQhqyiiHGrbNYYtlqbHK").mobile("+40723145619").address("333 Oak St, Paris, France").birthday(LocalDate.of(2003, 9, 8)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser20() { return User.builder().id(20).name("Isabella Anderson").email("isabella.anderson@example.com").password("$2a$10$wfSRBt.m9sSC.RmOe8lzJOsJlX7ajGujrdE1wmhRVEY753.DzYRD.").mobile("+40787654320").address("555 Elm St, London, UK").birthday(LocalDate.of(1999, 6, 22)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser21() { return User.builder().id(21).name("Mason Thompson").email("mason.thompson@example.com").password("$2a$10$ERa1AxKeQw1/eDMOS/ObleC/o7KWhKqsDNZmFUPMI5830cWZr/VLy").mobile("+40754321821").address("777 Pine St, Berlin, Germany").birthday(LocalDate.of(2002, 12, 20)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser22() { return User.builder().id(22).name("Charlotte Thompson").email("charlotte.thompson@example.com").password("$2a$10$0UjFhZHVhp2HBgjyedohUu0U6LJxARKlgExfUeAPyn4qTZFDqPOg2").mobile("+40789012622").address("999 Oak St, Moscow, Russia").birthday(LocalDate.of(1998, 10, 18)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser23() { return User.builder().id(23).name("Elijah Smith").email("elijah.smith@example.com").password("$2a$10$FDghGPE0AshKfF5R8EUPteMsPkAeeuxgU0iEKu31X7uo1FPqGqXn2").mobile("+40723145623").address("111 Elm St, Athens, Greece").birthday(LocalDate.of(2003, 5, 3)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser24() { return User.builder().id(24).name("Amelia Johnson").email("amelia.johnson@example.com").password("$2a$10$dkLjeeYMeprqj.fKHC.O/uoTdaPYmUj3iSWEOgqywxDKauEIQMmTO").mobile("+40787654324").address("333 Pine St, Madrid, Spain").birthday(LocalDate.of(1998, 12, 14)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser25() { return User.builder().id(25).name("Harper Wilson").email("harper.wilson@example.com").password("$2a$10$tM.taQiLPy0Dv.9eu.lq6OKzr1c5KvNj4jE0Wv.fH4LL895CyRsY.").mobile("+40754321825").address("555 Oak St, Tokyo, Japan").birthday(LocalDate.of(2001, 7, 27)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser26() { return User.builder().id(26).name("Daniel Thompson").email("daniel.thompson@example.com").password("$2a$10$NcaZuOZx20.pU8K95t0uBeuOi7L7LsqHNBZP1yPDnKpzyPiRbjqu6").mobile("+40789012626").address("777 Elm St, Seoul, South Korea").birthday(LocalDate.of(2001, 2, 9)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser27() { return User.builder().id(27).name("Liam Thompson").email("liam.thompson@example.com").password("$2a$10$HNmZy.y.aQHrFfJHuTfj4O3eZmmSEyGzjIpBAxh6NuqjUZIEfudJC").mobile("+40723145627").address("999 Oak St, Beijing, China").birthday(LocalDate.of(2000, 9, 23)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser28() { return User.builder().id(28).name("Grace Martinez").email("grace.martinez@example.com").password("$2a$10$sUhx0SNgqZJWZRKAnObyRu3QxkeNJ1PVwAM1dPMtPEBLd2UhXOFvW").mobile("+40787654328").address("111 Elm St, Cape Town, South Africa").birthday(LocalDate.of(2002, 6, 6)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser29() { return User.builder().id(29).name("Isabella White").email("isabella.white@example.com").password("$2a$10$DNt/zlBmhtAebwXf4RCyZ.hIIk3c2guAz9NcVt0qk/Ax3M9fK3Ef2").mobile("+40754321829").address("333 Pine St, Buenos Aires, Argentina").birthday(LocalDate.of(2002, 1, 19)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser30() { return User.builder().id(30).name("Logan Thompson").email("logan.thompson@example.com").password("$2a$10$qNkwGS7qKC9i2bOeB5SWJeVhFyWpk71NN6XCBAqNdnK1lVnQfygOG").mobile("+40789012630").address("555 Elm St, Rio de Janeiro, Brazil").birthday(LocalDate.of(2003, 7, 2)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser31() { return User.builder().id(31).name("Evelyn Brown").email("evelyn.brown@example.com").password("$2a$10$NDktEzYFJBMVc104nC9TJeQgabUxhdp4sXbNdjvESyOP6tii0k1.e").mobile("+40723145631").address("777 Oak St, Mexico City, Mexico").birthday(LocalDate.of(1999, 3, 16)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser32() { return User.builder().id(32).name("Henry Davis").email("henry.davis@example.com").password("$2a$10$ofOqGSbZwN/gKCyQ1dXhiOgG9KirJ39QwAvg2bfMdJSLabMWTL.qi").mobile("+40787654332").address("999 Elm St, Vancouver, Canada").birthday(LocalDate.of(1998, 10, 29)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser33() { return User.builder().id(33).name("Sofia Smith").email("sofia.smith@example.com").password("$2a$10$e/zTP55VBCo7VUPSiHkdB.q6w5L2pZS0STtujevXPlG1zGgz641AW").mobile("+40754321833").address("111 Oak St, Paris, France").birthday(LocalDate.of(2000, 6, 11)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser34() { return User.builder().id(34).name("Jack Wilson").email("jack.wilson@example.com").password("$2a$10$RTe09cmsz4R9JGssv2b/buTnVvmIYHGHD/vGfiglxjZARNCPMik6W").mobile("+40789012634").address("333 Elm St, London, UK").birthday(LocalDate.of(1997, 1, 24)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser35() { return User.builder().id(35).name("Emily Anderson").email("emily.anderson@example.com").password("$2a$10$E2lv05QvDikcobayv6ACoOHZHc1xVEDF1ffkLLiE29Jm941IJQ/rq").mobile("+40723145635").address("555 Pine St, Berlin, Germany").birthday(LocalDate.of(1995, 8, 6)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser36() { return User.builder().id(36).name("Benjamin Thompson").email("benjamin.thompson@example.com").password("$2a$10$4lnj3fxxnHMA36oeYBjDNuCHH8TbN3fMX0OB1ia6RpseCpQTG2qWe").mobile("+40787654336").address("777 Elm St, Moscow, Russia").birthday(LocalDate.of(1996, 3, 20)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser37() { return User.builder().id(37).name("Abigail Johnson").email("abigail.johnson@example.com").password("$2a$10$7UUcqTY7jdmZDBOBDiZ3g.HqGB6D06nJP/AwvPFV7rwo9uOaZNIlO").mobile("+40754321837").address("999 Oak St, Athens, Greece").birthday(LocalDate.of(2000, 10, 2)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser38() { return User.builder().id(38).name("Michael Davis").email("michael.davis@example.com").password("$2a$10$3auV5ZcsQ3BsY8.3oUrgPucCvU0eznOnu6rMzSCXBuAtk/PYijS/m").mobile("+40789012638").address("111 Oak St, Madrid, Spain").birthday(LocalDate.of(1994, 5, 16)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser39() { return User.builder().id(39).name("Mia Wilson").email("mia.wilson@example.com").password("$2a$10$MuET5mkpKltmeMVvuHd6V.7259YpfoILdK2.nddroh9faRLqLpHEm").mobile("+40723145639").address("333 Elm St, Tokyo, Japan").birthday(LocalDate.of(1990, 12, 29)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser40() { return User.builder().id(40).name("James Lee").email("james.lee@example.com").password("$2a$10$4FbH/OxBf07GkqQF4jZ0lewyz6848Bd9/FQDZlFpkWxVKCJoe2lFu").mobile("+40787654340").address("555 Pine St, Seoul, South Korea").birthday(LocalDate.of(1991, 8, 11)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser41() { return User.builder().id(41).name("Charlotte Thompson").email("charlotte.thompson@example.com").password("$2a$10$cBB9kPr6TTTuSoXGvemMpOM22GFC3luTSifaHUiGVSe1DD5/alV5O").mobile("+40754321841").address("777 Elm St, Beijing, China").birthday(LocalDate.of(1993, 3, 24)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser42() { return User.builder().id(42).name("Ethan Smith").email("ethan.smith@example.com").password("$2a$10$FvZzJA.PCFlv5b4NqYQBSuMjwDnFt2FGtjqXkhyNcDDPrp/gf5Oxe").mobile("+40789012642").address("999 Oak St, Cape Town, South Africa").birthday(LocalDate.of(1989, 11, 6)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser43() { return User.builder().id(43).name("Amelia Johnson").email("amelia.johnson@example.com").password("$2a$10$6d32lzlX2hB83fWixR5jeOroXywO1SVvUnvdyaaZ9wk4Pp5F7Yq0y").mobile("+40723145643").address("111 Elm St, Buenos Aires, Argentina").birthday(LocalDate.of(1994, 6, 19)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser44() { return User.builder().id(44).name("Emily Davis").email("emily.davis@example.com").password("$2a$10$n0mHfbplLnUpRNFbXEYD5OaQBeCjZzUsuRJ5H/S8ofINjKXECaZ.G").mobile("+40787654344").address("333 Elm St, Rio de Janeiro, Brazil").birthday(LocalDate.of(1998, 1, 1)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser45() { return User.builder().id(45).name("Henry Wilson").email("henry.wilson@example.com").password("$2a$10$AZ3jGKUs6D8lfhHUr3rx3uQ/0T476Fcht5TbiaDAFXbye450e9LyC").mobile("+40754321845").address("555 Pine St, Mexico City, Mexico").birthday(LocalDate.of(2001, 8, 14)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser46() { return User.builder().id(46).name("Scarlett Thompson").email("scarlett.thompson@example.com").password("$2a$10$oRPHmqBWyxqUUTp9/D7H5.yfGDfdec0/RiY.WzB7BhMPPcBkE6cSu").mobile("+40789012646").address("777 Elm St, Vancouver, Canada").birthday(LocalDate.of(2002, 3, 28)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser47() { return User.builder().id(47).name("Jacob Brown").email("jacob.brown@example.com").password("$2a$10$OO1Z6TYDthCdybZU/rceSOQfFAcsxvkZT2yBO9ucYgqENtQX.50va").mobile("+40723145647").address("999 Pine St, Paris, France").birthday(LocalDate.of(1999, 11, 10)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser48() { return User.builder().id(48).name("Ava Smith").email("ava.smith@example.com").password("$2a$10$Oo7exvPPj/LhlunzWwSHhuyTEPwd13A6cpZae9f9LtamdtJNAFoRO").mobile("+40787654348").address("111 Pine St, London, UK").birthday(LocalDate.of(1986, 6, 23)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser49() { return User.builder().id(49).name("Oliver Johnson").email("oliver.johnson@example.com").password("$2a$10$94fTudR/Hf9H8ikS6Ce/dO.i1VuvD/1zSWpVFJGQJn0b6JSwRlJZG").mobile("+40754321849").address("333 Elm St, Berlin, Germany").birthday(LocalDate.of(1988, 2, 5)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser50() { return User.builder().id(50).name("Sophia Wilson").email("sophia.wilson@example.com").password("$2a$10$5wChcsu5xUGj4u/H1yDIbe7xk8lAz8riAOe2BqGdj70K/HNcKjTRm").mobile("+40789012650").address("555 Elm St, Moscow, Russia").birthday(LocalDate.of(1994, 9, 19)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser51() { return User.builder().id(51).name("William Davis").email("william.davis@example.com").password("$2a$10$257Ppu30VNArsiC3urYLGuts6H7dZqZtZslzBvkXZK0lC59Rz//U2").mobile("+40723145651").address("777 Pine St, Athens, Greece").birthday(LocalDate.of(1996, 4, 3)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser52() { return User.builder().id(52).name("Mia Johnson").email("mia.johnson@example.com").password("$2a$10$LFMAamKO7DYmBGppJpxwDuLD4btdjty5jCokyUX/VIH6Hcml31rai").mobile("+40787654352").address("999 Oak St, Madrid, Spain").birthday(LocalDate.of(1998, 11, 16)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser53() { return User.builder().id(53).name("James Lee").email("james.lee@example.com").password("$2a$10$wqh8ddUuBBncfB//NDGGxuVO.ABJce5F9YwCT2QBUW2SQVenRKak2").mobile("+40754321853").address("111 Elm St, Tokyo, Japan").birthday(LocalDate.of(1997, 6, 29)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser54() { return User.builder().id(54).name("Charlotte Brown").email("charlotte.brown@example.com").password("$2a$10$WfHpWQ4nIFeQ.jaVEV0Ga.IXMZQewk6PFogdeJHAhCcrUfxjyDfrG").mobile("+40789012654").address("333 Pine St, Seoul, South Korea").birthday(LocalDate.of(2000, 2, 12)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser55() { return User.builder().id(55).name("Ethan Smith").email("ethan.smith@example.com").password("$2a$10$SuJZKKj2BG/Snz3Csehvq.yIh7sPpA./pJccTEvwzwDDH6dH5Txxm").mobile("+40723145655").address("555 Elm St, Beijing, China").birthday(LocalDate.of(1998, 9, 25)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser56() { return User.builder().id(56).name("Emily Davis").email("emily.davis@example.com").password("$2a$10$GudEvtjLoJSxTiZoCOYqmO4.OjWPQyCj1C6gWfYEpsjReeHl2LxjC").mobile("+40787654356").address("777 Elm St, Cape Town, South Africa").birthday(LocalDate.of(2001, 7, 9)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser57() { return User.builder().id(57).name("Jacob Thompson").email("jacob.thompson@example.com").password("$2a$10$2tlMlGt5xxn5wwx3wID52eGMdCjlGVtiCunQdsN0qYsdiIPAG1mX2").mobile("+40754321857").address("999 Pine St, Buenos Aires, Argentina").birthday(LocalDate.of(1996, 2, 22)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser58() { return User.builder().id(58).name("Sophia Wilson").email("sophia.wilson@example.com").password("$2a$10$WrJOjHuzHZZmiYauvD.CLuXCusKkhemkIXCe/s9bApcg3.VbcALTS").mobile("+40789012658").address("111 Elm St, Rio de Janeiro, Brazil").birthday(LocalDate.of(1995, 10, 7)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser59() { return User.builder().id(59).name("Oliver Johnson").email("oliver.johnson@example.com").password("$2a$10$2SL7v1wglwp.BpoljPgKke9gRzn46wyanmPRH1Qt7vQyllKRRbBbS").mobile("+40723145659").address("333 Elm St, Mexico City, Mexico").birthday(LocalDate.of(1998, 7, 21)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser60() { return User.builder().id(60).name("Scarlett Wilson").email("scarlett.wilson@example.com").password("$2a$10$/8XJPbpHCcBvLjL/0OUdN.FC8L/96z5kysz0dk7A3RUoVxgXCIrmC").mobile("+40787654360").address("555 Elm St, Vancouver, Canada").birthday(LocalDate.of(2001, 5, 5)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser61() { return User.builder().id(61).name("William Davis").email("william.davis@example.com").password("$2a$10$491zZkJmr8sEpEYoFY9zlOGWwSC5GkEd8lseuJY8M1Kt8v0VfcjB6").mobile("+40754321861").address("777 Pine St, Paris, France").birthday(LocalDate.of(1999, 12, 17)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser62() { return User.builder().id(62).name("Mia Johnson").email("mia.johnson@example.com").password("$2a$10$p92501aT9fP1OSFYjH84guX9Fryv7UTb0pIAuHQ8KzIs7jzzBHkuG").mobile("+40789012662").address("999 Oak St, London, UK").birthday(LocalDate.of(2002, 9, 30)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser63() { return User.builder().id(63).name("James Lee").email("james.lee@example.com").password("$2a$10$vfsknQZqRgxXc2PPGh4NYubY6Sz9u/OlV2NdS0koy1coxetYJBTZK").mobile("+40723145663").address("111 Elm St, Berlin, Germany").birthday(LocalDate.of(1996, 5, 14)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser64() { return User.builder().id(64).name("Charlotte Brown").email("charlotte.brown@example.com").password("$2a$10$9OE8rwchWegMy/FAcvMYU.vOSj0dNoIMzzzaiuRBgiyhkJkWU0Q8i").mobile("+40787654364").address("333 Elm St, Moscow, Russia").birthday(LocalDate.of(2002, 12, 27)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser65() { return User.builder().id(65).name("Elijah Roberts").email("elijah.roberts@example.com").password("$2a$10$XILV0oXm68X3a.Ym23xlG.Gk1tcQVR1ZhruRVXV7JJ62qGB4yc7u2").mobile("+40754321865").address("555 Pine St, Sydney, Australia").birthday(LocalDate.of(2003, 1, 27)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser66() { return User.builder().id(66).name("Amelia Walker").email("amelia.walker@example.com").password("$2a$10$dxOTaCQO.7RJK.cAsJ97iu9J4kLMdJXusmvSG2IYq8P454GAO/SSa").mobile("+40789012666").address("777 Oak St, Rome, Italy").birthday(LocalDate.of(1992, 8, 12)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser67() { return User.builder().id(67).name("Daniel Green").email("daniel.green@example.com").password("$2a$10$H5Esf6nFvsfooqzGbN4MoOG44HRT9YrgfeiuPwtZPJwce4VFq3q9e").mobile("+40723145667").address("999 Elm St, Moscow, Russia").birthday(LocalDate.of(1996, 3, 27)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser68() { return User.builder().id(68).name("Liam Hall").email("liam.hall@example.com").password("$2a$10$hHaSEzjoEsDuc./b.tuAguHhMrhAWfabZMlHVAwmmlv2t5LzqhjaW").mobile("+40787654368").address("111 Oak St, Athens, Greece").birthday(LocalDate.of(1998, 11, 9)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser69() { return User.builder().id(69).name("Sophia Young").email("sophia.young@example.com").password("$2a$10$hXkNymRKKR/ecaWDFpAjCuA4F.mFSIsMRG5pm3EcEpC52RTE/bmS2").mobile("+40754321869").address("333 Pine St, Madrid, Spain").birthday(LocalDate.of(1995, 6, 23)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser70() { return User.builder().id(70).name("Noah Clark").email("noah.clark@example.com").password("$2a$10$XDO5A5bCk.bDuJm1eKEF6./FIu51X2g9voB44GdXfb/6ICnH5MWmG").mobile("+40789012670").address("555 Elm St, Tokyo, Japan").birthday(LocalDate.of(1997, 2, 5)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser71() { return User.builder().id(71).name("Olivia Hill").email("olivia.hill@example.com").password("$2a$10$BU7Fl.RH3Bf66WjaQYTjq.vP5cLtK3WKlX4f3dfeEbuI..IgxKl3u").mobile("+40723145671").address("777 Oak St, Seoul, South Korea").birthday(LocalDate.of(2001, 9, 20)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static User getMockedUser72() { return User.builder().id(72).name("Michaela Allen").email("michaela.allen@example.com").password("$2a$10$jtNJLdz.PNuBgZWbBz/APOOjjipUSn9Qw6p1VmEM1PjYCBXZHI.Lm").mobile("+40787654372").address("999 Pine St, Beijing, China").birthday(LocalDate.of(1998, 5, 6)).role(getMockedRole1()).authorities(getMockedAuthorities()).build(); }

    public static UserDto getMockedUserDto1() { return UserDto.builder().id(1).name("John Doe").email("john.doe@example.com").password("$2a$10$2cEzAx1UKlGGlWai6VasvOUdVBg.NCug8XsdOyhwRdvLYYgFW.O/W").mobile("+40721543701").address("123 Main St, Boston, USA").birthday(LocalDate.of(1980, 2, 15)).roleId(2).authoritiesIds(getMockedAuthoritiesIds()).build(); }

    public static UserDto getMockedUserDto2() { return UserDto.builder().id(2).name("Jane Smith").email("jane.smith@example.com").password("$2a$10$tltXH0bchhhvzf6sWpZOduMfTdNFzORnJjo2quB9e8qTFttHj8RsG").mobile("+40756321802").address("456 Oak St, London, UK").birthday(LocalDate.of(1982, 7, 10)).roleId(2).authoritiesIds(getMockedAuthoritiesIds()).build(); }

    public static UserDto getMockedUserDto3() { return UserDto.builder().id(3).name("Michael Johnson").email("michael.johnson@example.com").password("$2a$10$IeJe8nsYE4MCiuIXTEk7uetLN/T5ynAKtApxJwVMBmDC6Bk3W3W3y").mobile("+40789712303").address("789 Pine St, Madrid, Spain").birthday(LocalDate.of(1990, 11, 20)).roleId(2).authoritiesIds(getMockedAuthoritiesIds()).build(); }

    public static UserDto getMockedUserDto4() { return UserDto.builder().id(4).name("Laura Brown").email("laura.brown@example.com").password("$2a$10$OvqxdzK0hef5A.jcW3PQOewRrZnafHtpAiAFn.aF8KW1ktaRvb5NO").mobile("+40734289604").address("333 Elm St, Paris, France").birthday(LocalDate.of(1985, 8, 25)).roleId(2).authoritiesIds(getMockedAuthoritiesIds()).build(); }

    public static UserDto getMockedUserDto5() { return UserDto.builder().id(5).name("Robert Davis").email("robert.davis@example.com").password("$2a$10$vpnwsCU8bOzYq2l8CljiBeOcZZRfVjeo4g96XnH2KYbqNRQYfZQh.").mobile("+40754321805").address("555 Oak St, Berlin, Germany").birthday(LocalDate.of(1988, 5, 12)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).build(); }

    public static UserDto getMockedUserDto6() { return UserDto.builder().id(6).name("Emily Wilson").email("emily.wilson@example.com").password("$2a$10$GNYbCsERVLU1Vw7L9obxAOX9spb8QgGSc8SSzAfQwGVIE/UMZ.ItK").mobile("+40789012606").address("777 Pine St, Sydney, Australia").birthday(LocalDate.of(1995, 9, 8)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).build(); }

    public static UserDto getMockedUserDto7() { return UserDto.builder().id(7).name("Michaela Taylor").email("michaela.taylor@example.com").password("$2a$10$ZRHHzDuEirPWk/GA22HQ1u/Rm6sNV4VPO9WhxYfgKSuCvF73AMIO6").mobile("+40723145607").address("999 Elm St, Rome, Italy").birthday(LocalDate.of(1983, 12, 7)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).build(); }

    public static UserDto getMockedUserDto8() { return UserDto.builder().id(8).name("David Anderson").email("david.anderson@example.com").password("$2a$10$A8YW129edU79eHirdlT.2u4cQQy5bCgJIBie2YrDyYfoUSi4i5/GO").mobile("+40787654308").address("111 Oak St, Moscow, Russia").birthday(LocalDate.of(1992, 4, 23)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).build(); }

    public static UserDto getMockedUserDto9() { return UserDto.builder().id(9).name("Sophia Garcia").email("sophia.garcia@example.com").password("$2a$10$uO2oGV6PM5aBrAmqR98LQu2FtC/iaFoXx2nsyNXuOhNbaEOzXKbx2").mobile("+40754321809").address("333 Pine St, Athens, Greece").birthday(LocalDate.of(1998, 7, 30)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).build(); }

    public static UserDto getMockedUserDto10() { return UserDto.builder().id(10).name("Joseph Wilson").email("joseph.wilson@example.com").password("$2a$10$aoNj5vm.RdmMDffhpCgpu.mCmhgbanqS1R1CHeFR1iSjv/cASQa4W").mobile("+40789012610").address("555 Elm St, Madrid, Spain").birthday(LocalDate.of(1991, 3, 14)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).build(); }
}
