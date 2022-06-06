from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
import getopt
import sys
from docx2pdf import convert
import os
import platform
from mailmerge import MailMerge  # 引用邮件处理模块

#-t "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS014\NST－04－JS014－2011 - 软件文档评审表.docx" -i "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS014\data.json" -o "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS014\output"

def main(argv):
      
  o_path = ""
  i_path = ""
  template = ""
  
  try:
    opts, args = getopt.getopt(argv,'o:i:t:',["ofile=","ifile=","template="])
  except getopt.GetoptError:
    sys.exit(2)
    
  for opt, arg in opts:
      if opt in ("-o", "--ofile"):
          o_path = arg
      elif opt in ("-i", "--ifile"):
          i_path = arg 
      elif opt in ("-t", "--template"):
          template = arg
  with open(i_path, 'r', encoding='utf8') as fp:
    jdata = json.load(fp)
    document = MailMerge(template)

    document.merge(
      SoftwareName = jdata["软件名称"],
      SoftwareVer = jdata["版本号"],
      Delegation = jdata["委托单位"],
      Viewer = jdata["评审人"],
      Checker = jdata["检查人"],
      Time = jdata["time"],
      
      Res1 = jdata["softwareReviewData"][0]["result"],
      Res2 = jdata["softwareReviewData"][1]["result"],
      Res3 = jdata["softwareReviewData"][2]["result"],
      Res4 = jdata["softwareReviewData"][3]["result"],
      Res5 = jdata["softwareReviewData"][4]["result"],
      Res6 = jdata["softwareReviewData"][5]["result"],
      Res7 = jdata["softwareReviewData"][6]["result"],
      Res8 = jdata["softwareReviewData"][7]["result"],
      Res9 = jdata["softwareReviewData"][8]["result"],
      Res10 = jdata["softwareReviewData"][9]["result"],
      Res11 = jdata["softwareReviewData"][10]["result"],
      Res12 = jdata["softwareReviewData"][11]["result"],
      Res13 = jdata["softwareReviewData"][12]["result"],
      Res14 = jdata["softwareReviewData"][13]["result"],

      Exp1 = jdata["softwareReviewData"][0]["explain"],
      Exp2 = jdata["softwareReviewData"][1]["explain"],
      Exp3 = jdata["softwareReviewData"][2]["explain"],
      Exp4 = jdata["softwareReviewData"][3]["explain"],
      Exp5 = jdata["softwareReviewData"][4]["explain"],
      Exp6 = jdata["softwareReviewData"][5]["explain"],
      Exp7 = jdata["softwareReviewData"][6]["explain"],
      Exp8 = jdata["softwareReviewData"][7]["explain"],
      Exp9 = jdata["softwareReviewData"][8]["explain"],
      Exp10 = jdata["softwareReviewData"][9]["explain"],
      Exp11 = jdata["softwareReviewData"][10]["explain"],
      Exp12 = jdata["softwareReviewData"][11]["explain"],
      Exp13 = jdata["softwareReviewData"][12]["explain"],
      Exp14 = jdata["softwareReviewData"][13]["explain"],

      Res15 = jdata["软件文档集评审"][0]["result"],
      Res16 = jdata["软件文档集评审"][1]["result"],
      Res17 = jdata["软件文档集评审"][2]["result"],
      Res18 = jdata["软件文档集评审"][3]["result"],
      Res19 = jdata["软件文档集评审"][4]["result"],
      Res20 = jdata["软件文档集评审"][5]["result"],
      Res21 = jdata["软件文档集评审"][6]["result"],
      Res22 = jdata["软件文档集评审"][7]["result"],
      Res23 = jdata["软件文档集评审"][8]["result"],
      Res24 = jdata["软件文档集评审"][9]["result"],
      Res25 = jdata["软件文档集评审"][10]["result"],
      Res26 = jdata["软件文档集评审"][11]["result"],
      Res27 = jdata["软件文档集评审"][12]["result"],
      Res28 = jdata["软件文档集评审"][13]["result"],
      Res29 = jdata["软件文档集评审"][14]["result"],
      Res30 = jdata["软件文档集评审"][15]["result"],
      Res31 = jdata["软件文档集评审"][16]["result"],
      Res32 = jdata["软件文档集评审"][17]["result"],
      Res33 = jdata["软件文档集评审"][18]["result"],
      Res34 = jdata["软件文档集评审"][19]["result"],
      
      Exp15 = jdata["软件文档集评审"][0]["explain"],
      Exp16 = jdata["软件文档集评审"][1]["explain"],
      Exp17 = jdata["软件文档集评审"][2]["explain"],
      Exp18 = jdata["软件文档集评审"][3]["explain"],
      Exp19 = jdata["软件文档集评审"][4]["explain"],
      Exp20 = jdata["软件文档集评审"][5]["explain"],
      Exp21 = jdata["软件文档集评审"][6]["explain"],
      Exp22 = jdata["软件文档集评审"][7]["explain"],
      Exp23 = jdata["软件文档集评审"][8]["explain"],
      Exp24 = jdata["软件文档集评审"][9]["explain"],
      Exp25 = jdata["软件文档集评审"][10]["explain"],
      Exp26 = jdata["软件文档集评审"][11]["explain"],
      Exp27 = jdata["软件文档集评审"][12]["explain"],
      Exp28 = jdata["软件文档集评审"][13]["explain"],
      Exp29 = jdata["软件文档集评审"][14]["explain"],
      Exp30 = jdata["软件文档集评审"][15]["explain"],
      Exp31 = jdata["软件文档集评审"][16]["explain"],
      Exp32 = jdata["软件文档集评审"][17]["explain"],
      Exp33 = jdata["软件文档集评审"][18]["explain"],
      Exp34 = jdata["软件文档集评审"][19]["explain"],
    )
    wordname = o_path + '.docx' 
    document.write(wordname)  # 创建新文件
    if platform.system() == "Windows":
          convert(wordname, o_path + '.pdf')
    else:
          os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")      
          
          
if __name__ == '__main__':
    main(sys.argv[1:])
    
