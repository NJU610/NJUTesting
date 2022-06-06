from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
import getopt
import sys
#from docx2pdf import convert
import os
from mailmerge import MailMerge  # 引用邮件处理模块

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

  
  #template = 'C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS005\\NST－04－JS006－2011－软件测试方案.docx'  # 模版位置
  with open(i_path, 'r', encoding='utf8') as fp:
    jdata = json.load(fp)
    
    document = MailMerge(template)

    document.merge(
      Client = jdata["委托单位"],
      SoftName = jdata["软件名称"],
      DelA = jdata["甲方法人代表"],
      DelB = jdata["乙方法人代表"],
      TimeA = jdata["甲方_日期"],
      TimeB = jdata["乙方_日期"]
    )

    wordname = o_path + '.docx' 
    document.write(wordname)  # 创建新文件
    #convert(wordname, o_path + '.pdf')
    os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")    
    
if __name__ == '__main__':
    main(sys.argv[1:])
    



      
    
