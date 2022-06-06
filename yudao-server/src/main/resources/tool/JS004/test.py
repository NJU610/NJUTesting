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

#-t "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS004\NST－04－JS004－2011－软件委托测试合同.docx" -i "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS004\data.json" -o "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS004\output"

def main(argv):
      
  o_path = ""
  i_path = ""
  template = ""
  
  try:
    opts, args = getopt.getopt(argv,'o:i:t:',["ofile=","ifile=","template="])
  except getopt.GetoptError:
    sys.exit(2)
  for opt, arg in opts:
      print(opt)
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
      Name = jdata["项目名称"],
      P1 = jdata["委托方(甲方)"],
      P2 = jdata["委托方(乙方)"],
      Place = jdata["签订地点"],
      Date = jdata["签订日期"],
      FirstParty = jdata["委托方(甲方)"],
      SoftName = jdata["软件名称"],
      QuaFea = jdata["质量特性"],
      Price = jdata["合同价款"],
      DDL = jdata["完成天数"],
      ChangeTimes = jdata["整改次数"],
      ChangeDays = jdata["超过天数"],
      PartyAName = jdata["委托方名称"],
      DelA = jdata["委托方授权代表"],
      SigDateA = jdata["委托方签章日期"],
      ContactA = jdata["委托方联系人"],
      AddrA = jdata["委托方通讯地址"],
      TelA = jdata["委托方电话"],
      FaxA = jdata["委托方传真"],
      BankA = jdata["委托方开户银行"],
      NumA = jdata["委托方账号"],
      PostA = jdata["委托方邮编"], #?
      DelB = jdata["受托方授权代表"],
      SigDateB = jdata["受托方签章日期"],
      ContactB = jdata["受托方联系人"],
      AddrB = jdata["受托方通讯地址"],
      PostB = jdata["受托方邮编"],
      TelB = jdata["受托方电话"],
      FaxB = jdata["受托方传真"]
      
    )
    wordname = o_path+ '.docx' 
    document.write(wordname)  # 创建新文件
    
    if platform.system() == "Windows":
          convert(wordname, o_path + '.pdf')
    else:
          os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")
      
    
if __name__ == '__main__':
    main(sys.argv[1:])