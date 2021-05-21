package br.com.zupacademy.fabio.proposta.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UserIp {
    static public String getIpAddress(HttpServletRequest servletRequest){
        final String LOCALHOST_IPV4 = "127.0.0.1";
        final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

        // pega o ip do usuário. Se for local pega o ip do host
        String ipAddress = "";
        ipAddress = servletRequest.getHeader("X-Forwarded-For");
        if(StringUtils.isEmpty(ipAddress)){
            ipAddress = servletRequest.getRemoteAddr();
            if(ipAddress.equals(LOCALHOST_IPV4) || ipAddress.equals(LOCALHOST_IPV6)){
                try{
                    InetAddress localHost = InetAddress.getLocalHost();
                    ipAddress = localHost.getHostAddress();
                }catch (UnknownHostException e){
                    e.printStackTrace();
                }
            }
        }
        return ipAddress;
    }
}
