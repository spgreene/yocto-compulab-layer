LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://README;md5=2456088a0455a82ac9e16b007de97c03"
DEPENDS = "u-boot-mkimage-native"

S = "${WORKDIR}"

inherit deploy

SRC_URI += "file://README \
	file://bootscr \
"

IBOOTSCRIPT ?= "bootscr"
OBOOTSCRIPT ?= "boot.scr"

do_mkimage () {
	# allow deploy to use the ${MACHINE} name to simplify things
	if [ ! -d board/compulab/${MACHINE} ]; then
		mkdir -p board/compulab/${MACHINE}
	fi

	uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
		-n "boot script" -d ${IBOOTSCRIPT} \
		board/compulab/${MACHINE}/${OBOOTSCRIPT}
}

addtask mkimage after do_compile before do_install

do_deploy () {
    install -d ${DEPLOYDIR}
    install ${S}/board/compulab/${MACHINE}/${OBOOTSCRIPT} \
            ${DEPLOYDIR}/${OBOOTSCRIPT}-${MACHINE}-${PV}-${PR}

    cd ${DEPLOYDIR}
    rm -f ${OBOOTSCRIPT}-${MACHINE}
    ln -sf ${OBOOTSCRIPT}-${MACHINE}-${PV}-${PR} ${OBOOTSCRIPT}-${MACHINE}
}

addtask deploy after do_install before do_build

do_compile[noexec] = "1"
do_install[noexec] = "1"
do_populate_sysroot[noexec] = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(cm-fx6-evk)"
