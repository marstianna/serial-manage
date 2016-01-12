package wsr

import (
	"errors"
	"fmt"
	"syscall"
	"unsafe"
)

var (
	initPswA string = "\xFF\xFF\xFF\xFF\xFF\xFF"
	initPswB string = "\xFF\xFF\xFF\xFF\xFF\xFF"
)

type Wsr struct {
	hWsr          syscall.Handle
	port          uintptr
	uuidBlk       uintptr
	uuidLen       uintptr
	passwdLen     int
	accessCodeLen int
	pswSector     int
}

func NewWsr() (*Wsr, error) {
	hWsr, err := syscall.LoadLibrary("WSR.DLL")
	if err != nil {
		return nil, errors.New("LoadLibrary failed, error:" + err.Error())
	}

	return &Wsr{
		hWsr:          hWsr,
		port:          0,
		uuidBlk:       6,
		uuidLen:       40,
		passwdLen:     6,
		accessCodeLen: 4,
		pswSector:     0,
	}, nil
}

func (this *Wsr) Destroy() error {
	return syscall.FreeLibrary(this.hWsr)
}

//find the com port and open it
func (this *Wsr) OpenPort() error {
	proc, err := syscall.GetProcAddress(this.hWsr, "ws_openPort")
	if err != nil {
		return errors.New("failed to GetProcAddress of ws_openPort, error:" + err.Error())
	}

	//find the port
	for port := 0; port <= 255; port++ {
		r1, _, _ := syscall.Syscall(uintptr(proc), 1, uintptr(port), 0, 0)
		if int32(r1) >= 0 {
			this.port = uintptr(port)
			fmt.Println("open port ", port, " succ")
			return nil
		}
	}

	return errors.New("no com port found")
}

//close the com port
func (this *Wsr) ClosePort() error {
	proc, err := syscall.GetProcAddress(this.hWsr, "ws_closePort")
	if err != nil {
		return errors.New("failed to GetProcAddress of ws_closePort, error:" + err.Error())
	}
	r1, _, _ := syscall.Syscall(uintptr(proc), 1, this.port, 0, 0)
	if int32(r1) >= 0 {
		return nil
	}

	return errors.New("close port failed")
}

func (this *Wsr) Beep() error {
	proc, err := syscall.GetProcAddress(this.hWsr, "ws_beep")
	if err != nil {
		return errors.New("failed to GetProcAddress of ws_beep, error:" + err.Error())
	}

	r1, _, _ := syscall.Syscall(uintptr(proc), 1, this.port, 0, 0)
	if int32(r1) >= 0 {
		return nil
	}

	return errors.New(getErrorMsg(int32(r1)))
}

func (this *Wsr) GetCardNo() (string, error) {
	proc, err := syscall.GetProcAddress(this.hWsr, "ws_getCardNo_String")
	if err != nil {
		return "", errors.New("failed to GetProcAddress of ws_getCardNo_String, error:" + err.Error())
	}

	cardNo := make([]byte, 11)
	r1, _, _ := syscall.Syscall(uintptr(proc), 2, this.port, uintptr(unsafe.Pointer(&cardNo[0])), 0)
	if int32(r1) >= 0 {
		return string(cardNo), nil
	}

	return "", errors.New(getErrorMsg(int32(r1)))
}

func (this *Wsr) ReadUuid() (string, error) {
	proc, err := syscall.GetProcAddress(this.hWsr, "ws_readData")
	if err != nil {
		return "", errors.New("failed to GetProcAddress of ws_readData, error:" + err.Error())
	}

	uuid := make([]byte, this.uuidLen)
	r1, _, _ := syscall.Syscall6(uintptr(proc), 4,
		this.port, this.uuidBlk, uintptr(unsafe.Pointer(&uuid[0])), uintptr(this.uuidLen),
		0, 0)
	if int32(r1) >= 0 {
		return string(uuid), nil
	}

	return "", errors.New(getErrorMsg(int32(r1)))
}

func (this *Wsr) WriteUuid(uuid string) error {
	if uintptr(len(uuid)) != this.uuidLen {
		return errors.New("uuid length error")
	}

	proc, err := syscall.GetProcAddress(this.hWsr, "ws_writeData")
	if err != nil {
		return errors.New("failed to GetProcAddress of ws_writeData, error:" + err.Error())
	}

	uuidBytes := []byte(uuid)
	r1, _, _ := syscall.Syscall6(uintptr(proc), 4,
		this.port, this.uuidBlk, uintptr(unsafe.Pointer(&uuidBytes[0])), this.uuidLen,
		0, 0)
	if int32(r1) >= 0 {
		return nil
	}

	return errors.New(getErrorMsg(int32(r1)))
}

//load passwd
//pswType: 0-Password A(default), 1-Password B
func (this *Wsr) LoadPsw(passwd string, pswType int32) error {
	if len(passwd) != this.passwdLen {
		return errors.New("wrong password length")
	}

	proc, err := syscall.GetProcAddress(this.hWsr, "ws_loadKey")
	if err != nil {
		return errors.New("failed to GetProcAddress of ws_loadKey, error:" + err.Error())
	}

	passwdBytes := []byte(passwd)
	r1, _, _ := syscall.Syscall(uintptr(proc), 3,
		this.port, uintptr(unsafe.Pointer(&passwdBytes[0])), uintptr(pswType),
	)
	if int32(r1) >= 0 {
		return nil
	}

	return errors.New(getErrorMsg(int32(r1)))
}

func (this *Wsr) SetPsw(pswA string, accessCode string, pswB string) error {
	if len(pswA) != this.passwdLen || len(pswB) != this.passwdLen || len(accessCode) != this.accessCodeLen {
		return errors.New("parameter length error")
	}

	proc, err := syscall.GetProcAddress(this.hWsr, "ws_changeKeyA")
	if err != nil {
		return errors.New("failed to GetProcAddress of ws_changeKeyA, error:" + err.Error())
	}

	paBytes := []byte(pswA)
	pbBytes := []byte(pswB)
	pacBytes := []byte(accessCode)
	r1, _, _ := syscall.Syscall6(uintptr(proc), 5,
		uintptr(this.port),
		uintptr(this.pswSector),
		uintptr(unsafe.Pointer(&paBytes[0])),
		uintptr(unsafe.Pointer(&pacBytes[0])),
		uintptr(unsafe.Pointer(&pbBytes[0])),
		0)
	if int32(r1) >= 0 {
		return nil
	}

	return errors.New(getErrorMsg(int32(r1)))
}
