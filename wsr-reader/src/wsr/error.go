package wsr

var errorMap map[int32]string = make(map[int32]string, 50)

//成功
const ERR_SUCC int32 = 0

//基础设施错误

const ERR_LOAD_LIB int32 = 1000
const ERR_INTERFACE int32 = 1002
const ERR_COM_PORT_NOTFOUND int32 = 1003

//业务错误
const ERR_UUID_LENGTH int32 = 2000
const ERR_LOAD_PSW_LEN int32 = 2001
const ERR_SET_PSW_LEN int32 = 2002

func init() {
	errorMap[ERR_SUCC] = "成功"
	errorMap[ERR_LOAD_LIB] = "链接库加载失败"
	errorMap[ERR_INTERFACE] = "找不到对应的函数地址"
	errorMap[ERR_COM_PORT_NOTFOUND] = "找不到通讯端口"
	errorMap[ERR_UUID_LENGTH] = "写入UUID时, 长度错误"
	errorMap[ERR_LOAD_PSW_LEN] = "加载密码时, 密码长度错误"
	errorMap[ERR_SET_PSW_LEN] = "设置密码时, 密码长度错误"
	errorMap[-1] = "参数值不正确"

	errorMap[-10] = "(仅修改密码)装载密码失败"
	errorMap[-11] = "(仅修改密码)读数据失败"
	errorMap[-12] = "(仅修改密码)写数据失败"

	errorMap[-100] = "写串口失败"
	errorMap[-101] = "读串口失败"
	errorMap[-102] = "接收的数据无效"

	errorMap[-226] = "卡不存在/卡坏"
	errorMap[-227] = "防冲撞错误"
	errorMap[-228] = "锁定卡出错"
	errorMap[-229] = "传密钥出错"
	errorMap[-230] = "密码验证出错"
	errorMap[-231] = "读块数据出错"
	errorMap[-232] = "写块数据出错"
	errorMap[-233] = "复位 RC500 失败"
	errorMap[-234] = "钱包值调入缓冲区出错"
	errorMap[-235] = "保存缓冲区值中的钱包值出错"
	errorMap[-236] = "增值出错"
	errorMap[-238] = "减值出错"
	errorMap[-240] = "块值格式不对"
	errorMap[-241] = "块值不够减"
	errorMap[-242] = "值溢出"
}

func GetErrorMsg(code int32) string {
	if code >= 0 && code < 1000 {
		return "成功"
	}

	v, ok := errorMap[code]
	if ok {
		return v
	}

	return "未知错误"
}
