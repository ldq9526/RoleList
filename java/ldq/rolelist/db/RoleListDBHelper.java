package ldq.rolelist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LDQ on 2017/10/7.
 */

public class RoleListDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_ROLE = "create table Role("
            +"name text primary key,"
            +"romaji text,"
            +"sex text,"
            +"blood text,"
            +"birthday text,"
            +"note text)";
    private static final String INSERT_ROLE = "insert into Role(name,romaji,sex,blood,birthday,note) values(?,?,?,?,?,?)";
    private static final String [][] roles = {
            {"比企谷八幡","Hikigaya Hachiman","男","A","8-8","就算不结婚,有妹妹不就好了嘛?"},
            {"比企谷小町","Hikigaya Komachi","女","O","3-3","长大做哥哥的新娘!"},
            {"雪之下阳乃","Yukinoshita Haruno","女","B","7-7","小雪乃不要比企谷君的话姐姐就收下了哟~"},
            {"雪之下雪乃","Yukinoshita Yukino","女","B","1-3","以眼还眼,以牙还牙!"},
            {"由比滨结衣","Yuigahama Yui","女","O","6-18","人生苦短,恋爱吧少女!"},
            {"一色彩羽","Isshiki Iroha","女","B","4-16","虽然很感动但是请再说一遍~前辈~"},
            {"川崎沙希","Kawasaki Saki","女","A","10-26","啊?"},
            {"鹤见留美","Tsurumi Rumi","女","","","笨蛋~笨蛋~"},
            {"平冢静","Hiratsuka Shizuka","女","A","","好想结婚TAT"},
            {"城廻巡","Shiromeguri meguri","女","","1-21","~巡巡能量~"},
            {"海老名姬菜","Ebina Hina","女","AB","7-14","叶X八~咕嘿嘿……"},
            {"三浦优美子","Miura Yumiko","女","B","12-12","哈(蛤)?"},
            {"折本香织","Orimoto Kaori","女","","2-21","这个可以有！超搞笑！"},
            {"户冢彩加","Totsuka Saika","男","A","5-9","我是男孩子哟~"}
    };
    private static final String CREATE_TAG = "create table Tag(name text,tag text)";
    private static final String INSERT_TAG = "insert into Tag(name,tag) values(?,?)";
    private static final String [][] tags = {
            {"比企谷八幡","蹲家"},
            {"比企谷八幡","死妹控"},
            {"比企谷八幡","自闭男"},
            {"比企谷八幡","小企鹅"},
            {"比企谷八幡","比企鹅"},
            {"比企谷八幡","别扭娇"},
            {"比企谷八幡","废柴哥哥"},
            {"比企谷八幡","死鱼眼"},
            {"比企谷八幡","祖传呆毛"},
            {"比企谷八幡","高二病"},
            {"比企谷八幡","大老师"},
            {"比企谷八幡","比企谷菌"},

            {"比企谷小町","世界第一可爱的妹妹"},
            {"比企谷小町","助攻王"},
            {"比企谷小町","祖传呆毛"},
            {"比企谷小町","兄控"},
            {"比企谷小町","笨蛋"},

            {"雪之下阳乃","美人"},
            {"雪之下阳乃","美丽的大姐姐"},
            {"雪之下阳乃","巨乳"},

            {"雪之下雪乃","小雪乃"},
            {"雪之下雪乃","黑长直裤袜"},
            {"雪之下雪乃","傲娇"},
            {"雪之下雪乃","毒舌"},
            {"雪之下雪乃","猫奴"},
            {"雪之下雪乃","雪百科"},
            {"雪之下雪乃","冰山美人"},
            {"雪之下雪乃","猫系美少女"},
            {"雪之下雪乃","贫乳"},
            {"雪之下雪乃","潘先生控"},

            {"由比滨结衣","团子"},
            {"由比滨结衣","巨乳"},
            {"由比滨结衣","现充"},
            {"由比滨结衣","八方美人"},
            {"由比滨结衣","八面玲珑"},
            {"由比滨结衣","气氛大师"},
            {"由比滨结衣","犬系美少女"},
            {"由比滨结衣","犬派"},
            {"由比滨结衣","粘人"},
            {"由比滨结衣","可爱"},
            {"由比滨结衣","笨蛋"},

            {"一色彩羽","小恶魔"},
            {"一色彩羽","烦人的后辈"},
            {"一色彩羽","烦人的学妹"},
            {"一色彩羽","小聪明"},
            {"一色彩羽","伪巡"},
            {"一色彩羽","完全不可爱的小町"},
            {"一色彩羽","强化版相模"},

            {"川崎沙希","川什么同学"},
            {"川崎沙希","青色马尾"},
            {"川崎沙希","青色单马尾"},

            {"鹤见留美","黑长直小学生"},

            {"平冢静","黑长直"},
            {"平冢静","女教师"},
            {"平冢静","女老师"},
            {"平冢静","静可爱"},
            {"平冢静","小静"},
            {"平冢静","大龄剩女"},

            {"城廻巡","巡巡能量~"},
            {"城廻巡","治愈"},
            {"城廻巡","天然呆"},

            {"海老名姬菜","腐女眼镜娘"},

            {"三浦优美子","三浦大妈"},
            {"三浦优美子","母老虎"},

            {"折本香织","初恋点赞狂魔"},

            {"户冢彩加","彩加小天使"},
            {"户冢彩加","可爱的男孩子"},
            {"户冢彩加","可爱的男孩纸"},
            {"户冢彩加","伪娘"}
    };

    public RoleListDBHelper(Context context, String name, CursorFactory cursorFactory,int version) {
        super(context,name,cursorFactory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ROLE);
        for (String[] role : roles) {
            db.execSQL(INSERT_ROLE, role);
        }
        db.execSQL(CREATE_TAG);
        for (String[] tag : tags) {
            db.execSQL(INSERT_TAG, tag);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
