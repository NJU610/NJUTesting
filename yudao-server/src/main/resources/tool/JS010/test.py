from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
import sys
import getopt
from docx2pdf import convert
import platform
import os
from mailmerge import MailMerge  # 引用邮件处理模块

#-t "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS010\NST－04－JS010－2011－测试报告检查表.docx" -i "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS010\data.json" -o "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS010\output"
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
      Del = jdata["委托单位"],
      SoftName = jdata["软件名称"],
      Reviewer = jdata["检查人"],
      Date = jdata["日期"],
      
      Res1 = jdata["dataSource"][0]["result"],
      Res2 = jdata["dataSource"][1]["result"],
      Res3 = jdata["dataSource"][2]["result"],
      Res4 = jdata["dataSource"][3]["result"],
      Res5 = jdata["dataSource"][4]["result"],
      Res6 = jdata["dataSource"][5]["result"],
      Res7 = jdata["dataSource"][6]["result"],
      Res8 = jdata["dataSource"][7]["result"],
      Res9 = jdata["dataSource"][8]["result"],
      Res10 = jdata["dataSource"][9]["result"],
      Res11 = jdata["dataSource"][10]["result"],
      Res12 = jdata["dataSource"][11]["result"],
      Res13 = jdata["dataSource"][12]["result"],
      Res14 = jdata["dataSource"][13]["result"],
    )
    wordname = o_path + '.docx' 
    document.write(wordname)  # 创建新文件
    if platform.system() == "Windows":
          convert(wordname, o_path + '.pdf')
    else:
          os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")
    
    
if __name__ == '__main__':
    main(sys.argv[1:])


      
    
