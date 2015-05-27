package jhttpserver.config;

import com.google.gson.Gson;
import jhttpserver.utils.PathUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jayin on 15/5/23.
 */
public class ServerConfig {

    public int port;
    public String root;
    public boolean cors;
    public List<String> DirectoryIndex;

    public static ServerConfig getInstance(){
        String config_file = PathUtil.getUserDir() + "/JHttpServer-core/config/server.config.json";
        try {
            Gson gson  = new Gson();
            return gson.fromJson(FileUtils.readFileToString(new File(config_file)), ServerConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "port=" + port +
                ", root='" + root + '\'' +
                ", cors=" + cors +
                ", DirectoryIndex=" + DirectoryIndex.toString() +
                '}';
    }

    public static void main(String[] argv){
        System.out.println(ServerConfig.getInstance().toString());
    }
}
