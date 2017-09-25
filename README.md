# LongPictureShare
长图拼接
    说明：三个页面的跳转，然后每个页面形成一张图片，进行拼接。。。
    
    1.书写界面
        2.存储需要画图的界面
        3.画图（Bitmap.createBitmap）
        4.合成图片
        5.保存图片到本地
        
    public class Utils {
    
        private static ArrayList<LinearLayout> mViewArrayList = new ArrayList<>();
    
        public static void addView(LinearLayout v) {
            mViewArrayList.add(v);
        }
    
        public static ArrayList<LinearLayout> getView() {
            return mViewArrayList;
        }
    }
    
        每个页面进行跳转前，保存需要画图的控件；
        Utils.addView(viewById);
        
        画图：
          for (LinearLayout scrollView : listView) {
                    // 获取listView实际高度
                    h = 0;
        
                    for (int i = 0; i < scrollView.getChildCount(); i++) {
                        h += scrollView.getChildAt(i).getHeight();
        
                        // 设置背景底色
                       /* int background = scrollView.getChildAt(i).getDrawingCacheBackgroundColor();
                        scrollView.getChildAt(i).setBackgroundColor(background);*/
                    }
        
                    // 创建对应大小的bitmap
                    Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    scrollView.draw(canvas);
        
                    bitmaps.add(bitmap);
                }
        
        合成图片：
                public static Bitmap toConformBitmap(ArrayList<Bitmap> bitmaps   /*Bitmap head, Bitmap kebiao, Bitmap san*/) {
                        if (bitmaps == null) {
                            return null;
                        }
                
                        Bitmap first = null;
                        Bitmap second = null;
                        Bitmap third = null;
                
                        for (int i = 0; i < bitmaps.size(); i++) {
                            Bitmap bitmap = bitmaps.get(i);
                
                            if (i == 0) {
                                first = bitmap;
                            } else if (i == 1) {
                                second = bitmap;
                            } else {
                                third = bitmap;
                            }
                        }
                
                        // 获取图片的宽高
                        int headWidth = first.getWidth();
                        int secondWidth = second.getWidth();
                        int lastWidth = third.getWidth();
                
                        int headHeight = first.getHeight();
                        int secondHeight = second.getHeight();
                        int lastHeight = third.getHeight();
                
                        //生成三个图片合并大小的Bitmap
                        Bitmap newBmp = Bitmap.createBitmap(secondWidth, headHeight + secondHeight + lastHeight, Bitmap.Config.ARGB_8888);
                        Canvas cv = new Canvas(newBmp);
                        cv.drawBitmap(first, 0, 0, null);// 在 0，0坐标开始画入headBitmap
                
                        //因为手机不同图片的大小的可能小了 就绘制白色的界面填充剩下的界面
                        if (headWidth < secondWidth) {
                            System.out.println("绘制头");
                
                            Bitmap ne = Bitmap.createBitmap(secondWidth - headWidth, headHeight, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(ne);
                            canvas.drawColor(Color.WHITE);
                            cv.drawBitmap(ne, headWidth, 0, null);
                        }
                
                        cv.drawBitmap(second, 0, headHeight, null);// 在 0，headHeight坐标开始填充第二张Bitmap
                        cv.drawBitmap(third, 0, headHeight + secondHeight, null);// 在 0，headHeight + secondHeight 坐标开始第三张图片
                
                        //因为手机不同图片的大小的可能小了 就绘制白色的界面填充剩下的界面
                        if (lastWidth < secondWidth) {
                            System.out.println("绘制");
                            Bitmap ne = Bitmap.createBitmap(secondWidth - lastWidth, lastHeight, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(ne);
                            canvas.drawColor(Color.WHITE);
                            cv.drawBitmap(ne, lastWidth, headHeight + secondHeight, null);
                        }
                
                        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
                        cv.restore();// 存储
                
                        //回收
                        first.recycle();
                        second.recycle();
                        third.recycle();
                
                        return newBmp;
                    }
        
        保存到本地：
             public static boolean saveImageToGallery(Context context, Bitmap bmp) {
            
                    // 首先保存图片
                    String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "medbanks";
                    File appDir = new File(storePath);
            
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
            
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File file = new File(appDir, fileName);
            
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        //通过io流的方式来压缩保存图片
                        boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                        fos.flush();
                        fos.close();
            
                        //把文件插入到系统图库
                        //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            
                        //保存图片后发送广播通知更新数据库
                        Uri uri = Uri.fromFile(file);
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            
                        if (isSuccess) {
                            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                            return true;
                        } else {
                            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                            return false;
                        }
            
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            
                    return false;
                }