package model.model2;

import com.syuio.annotation.BasisProtocol;
import com.syuio.annotation.Protocol;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 19:23
 */
@BasisProtocol
@Protocol(mType = 2)
public class User2 {

    @Protocol(mType = 2)
    public static class User3{
        public String name;
    }

    @Protocol(mType = 2)
    public static class User4{
        public String name;
    }

    public static class User5{
        public String name;
    }
}
