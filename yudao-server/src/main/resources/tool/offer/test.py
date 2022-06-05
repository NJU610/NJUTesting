from signal import SIGABRT
from sqlite3 import DatabaseError
from tkinter import PROJECTING
import xlrd  # 引入Excel读取模块
import json
import xlwt
import getopt
import sys
from docx2pdf import convert
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
  with open(i_path, 'r', encoding='utf8') as fp:
    jdata = json.load(fp)
    document = MailMerge(template)

    document.merge(
      Project2 = "",
      Item2 = "",
      Price2 = "",
      Brief2 = "",
      LTotal2 = "",
      
      Privider = jdata["报价提供人"],
      Date = jdata["报价日期"],
      StartDate = jdata["报价有效期"][0],
      EndDate = jdata["报价有效期"][1],
      Subtotal = jdata["小计"],
      Total = jdata["总计"],
      Tax = jdata["税率（8%）"],
      
      Project1 = jdata["项目表格"][0]["xiangmu"],
      Item1 = jdata["项目表格"][0]["fenxiang"],
      Price1 = jdata["项目表格"][0]["danjia"],
      Brief1 = jdata["项目表格"][0]["shuoming"],
      LTotal1 = jdata["项目表格"][0]["hangheji"],
    )
    wordname = o_path + '.docx' 
    document.write(wordname)  # 创建新文件
    convert(wordname, o_path + '.pdf')


if __name__ == '__main__':
    main(sys.argv[1:])
      
    
