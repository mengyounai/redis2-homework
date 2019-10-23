import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

public class connect {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost",6379);//默认端口6379 可以省略
//       jedis.auth("redis");  //有密码此步需加上

        Article article=new Article();
        article.setTitle("nmsl");
        article.setAuthor("jsl10");
        article.setContent("nmmcbzj");
        article.setTime("2019-10-25");
        AddArticle(article,jedis);
//        DelArticle("post",jedis);
//        UpTitle(article,"nmsl2019-10-27",jedis);
    }

    public static void AddArticle(Article article,Jedis jedis){
        long post = jedis.incr("post1");
        String article1 = JSON.toJSONString(article);
        String posts = article.getTitle()+post+article.getTime();
        jedis.set(posts,article1);
    }
    public static void DelArticle(String articleName,Jedis jedis){
        jedis.del(articleName);
    }
    public static void UpTitle(Article article,String articlName,Jedis jedis){
        article.setTitle("ccccc");
        String article1 = JSON.toJSONString(article);
        jedis.set(articlName,article1);
    }
}