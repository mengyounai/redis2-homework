import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import sun.security.krb5.internal.crypto.Aes128CtsHmacSha1EType;

import java.util.HashMap;
import java.util.Map;

public class HsetDemo {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost",6379);//默认端口6379 可以省略
//       jedis.auth("redis");  //有密码此步需加上

        Article article=new Article();
        article.setTitle("nmsl");
        article.setAuthor("jsl");
        article.setContent("nmmcbzj");
        article.setTime("2019-10-30");


//        Long articleId=saveArticle(article,jedis);
//        System.out.println("保存成功");
//        Article myarticle=getArticle(articleId,jedis);
//        System.out.println(myarticle);

//        DelArticle2(1,"title",jedis);

//        UpTitle2("title",1,"bbbbb",jedis);


          fenye("fenye",1,3,jedis);

//        AddArticle(article,jedis);
//        DelArticle("nmsl12019-10-23",jedis);
//        UpTitle(article,"nmsl22019-10-23",jedis);
    }

    public static void AddArticle(Article article,Jedis jedis){
        long post = jedis.incr("post");
        String article1 = JSON.toJSONString(article);
        String posts = article.getTitle()+post+article.getTime();
        jedis.set(posts,article1);
    }
    public static void DelArticle(String articleName,Jedis jedis){
        jedis.del(articleName);
    }
    public static void UpTitle(Article article,String articlName,Jedis jedis){
        article.setTitle("aaaaaa");
        String article1 = JSON.toJSONString(article);
        jedis.set(articlName,article1);
    }


 //   -----------------------------------------------------------------


    static Long saveArticle(Article article, Jedis jedis){
        Long articleId=jedis.incr("articleId");
        Map<String,String> blog=new HashMap<String, String>();
        blog.put("title",article.getTitle());
        blog.put("author",article.getAuthor());
        blog.put("content",article.getContent());
        jedis.hmset("article:"+articleId+":data",blog);
        jedis.rpush("fenye","article:"+articleId+":data");
        return articleId;
    }

    static Article getArticle(Long articleId, Jedis jedis){
        Map<String,String> myBlog=jedis.hgetAll("article:"+articleId+":data");
        Article article=new Article();
        article.setTitle(myBlog.get("title"));
        article.setContent(myBlog.get("content"));
        article.setAuthor(myBlog.get("author"));
        return article;
    }
    public static void DelArticle2(int articleId,String keys,Jedis jedis){
        jedis.hdel("article:"+articleId+":data",keys);
    }

    public static void UpTitle2(String key,long articleId,String val,Jedis jedis){
        Map<String,String> a=jedis.hgetAll("article:"+articleId+":data");
        a.put(key,val);
        jedis.hmset("article:"+articleId+":data",a);
    }
    public static void fenye(String name,int page,int size,Jedis jedis){
        jedis.lrange(name,page,size);
        System.out.println( jedis.lrange(name,1,3));
    }


}