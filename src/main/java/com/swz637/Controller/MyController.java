package com.swz637.Controller;

import com.swz637.Bean.Goods;
import com.swz637.Service.GoodsServiceImpl;
import com.swz637.Utils.StringUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpRetryException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ author: lsq637
 * @ since: 2022-09-21 14:58:43
 * @ describe:
 */
@Controller
public class MyController {

    @Autowired
    private GoodsServiceImpl goodsService;
    private static List<Goods> cache = null;//自定义的一个缓存，缓存上一页的查询结果，当翻页越界时，将缓存取出展示

    @RequestMapping("/upload")
    public String upload(@RequestParam("goodsImg") MultipartFile file, HttpServletRequest request,
                         Model model, Goods goods) throws IOException {

        // 判断文件是否为空，不为空才进行下方操作（允许用户不上传物品图片）
        if (!(file == null || file.isEmpty())) {
            // 获取文件存储路径（绝对路径）
            String path = ResourceUtils.getURL("classpath:").getPath().replaceFirst("/", "")
                    + "/static/img/upload";

            String path2 = "D:\\java\\expirationDate\\src\\main\\resources\\static\\img\\upload";

            //使用正则表达式获得文件的后缀名
            String originalFilename = file.getOriginalFilename();
            String regex = "^*(.[A-Za-z]+)$";
            Matcher matcher = Pattern.compile(regex).matcher(originalFilename);
            String suffix = "";
            if (matcher.find()){
                suffix = matcher.group(1);
            }

            //创建一个新的文件名防止重名
            String fileName =StringUtils.subStringFromTail(
                    UUID.randomUUID().toString(),12) + suffix;

            // 创建文件实例
            File filePath = new File(path, fileName);
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            //将接受的图片转换为对应文件路径下的图片文件
            file.transferTo(filePath);

            //将项目运行目录（target）下的上传的图片拷贝一份到项目构建目录（src）
            File file1 = new File(path2, fileName);
            InputStream inputStream = new FileInputStream(filePath);
            FileOutputStream outputStream = new FileOutputStream(file1);

            byte[] b = new byte[1024];
            while (inputStream.read(b) != -1) {
                outputStream.write(b);
            }
            outputStream.close();
            inputStream.close();

            goods.setProductImg("/img/upload/" + fileName);//在数据库中保存文件的引用地址
        }
        goodsService.addGoods(goods);

        return "redirect:/list/false";
    }

    /**
     * @param model
     * @param request
     * @param isExpired 表示商品是否过期，false代表没过期
     * @return
     * @throws HttpRetryException
     */
    @RequestMapping("/list/{isExpired}")
    public String expired(Model model, HttpServletRequest request, @PathVariable Boolean isExpired) throws HttpRetryException {
        String f = request.getParameter("f");//标记上一页，或者下一页
        List<Goods> goodsList = null;

        try {
            goodsList = goodsService.selectGoodsLimit(f, isExpired);
            if (goodsList != null) {//如果没出异常，将结果放入缓存
                cache = goodsList;
            }
        } catch (HttpRetryException e) {
            throw e;
        } catch (Exception e) {//若发生异常，将提示发送到前端，并取出上一个请求的缓存
            model.addAttribute("errorMsg", e.getMessage());
            goodsList = cache;
        }
        model.addAttribute("goodsList", goodsList);

        return isExpired ? "expired" : "unexpired";
    }

    @RequestMapping(value = {"/list/true/delete", "/list/false/delete"})
    public String delete(Model model, HttpServletRequest request) throws FileNotFoundException {
        Integer id = Integer.parseInt(request.getParameter("id"));

        //删除本地保存的图片
        //图片的访问路径/img/upload/4e546bb100b4.jpg
        String productImgPath = goodsService.selectById(id).getProductImg();
        if (productImgPath!=null){
            //本地idea的项目路径
            String imgPathOfWork = "D:\\java\\expirationDate\\src\\main\\resources\\static\\" + productImgPath;
            //项目target的路径
            String imgPathOfClass = ResourceUtils.getURL("classpath:").getPath().
                    replaceFirst("/", "")  + "/static" + productImgPath;

            new File(imgPathOfWork).delete();
            new File(imgPathOfClass).delete();
        }
        goodsService.deleteById(id);

        if (request.getRequestURI().contains("true")) {//在哪个页面删除的，就回到哪个页面
            return "redirect:/list/true";
        } else {
            return "redirect:/list/false";
        }
    }

    @RequestMapping(value = {"/list/true/toUpdate", "/list/false/toUpdate"})
    public String toUpdate(Model model, HttpServletRequest request) {//转跳到收集修改后信息的页面，
        // 同时将要修改的物品信息查询出来
        String id = request.getParameter("id");
        Goods goods = goodsService.selectById(Integer.parseInt(id));
        model.addAttribute("goods", goods);
        return "/update";
    }

    @RequestMapping("/update")
    public String update(Goods goods) {//完成更新动作
        goodsService.update(goods);
        return "redirect:/list/false";
    }

}
