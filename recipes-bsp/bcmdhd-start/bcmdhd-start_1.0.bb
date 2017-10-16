SUMMARY = "BCM4343W WiFi Start Scripts"
DESCRIPTION = "initscripts to set up the BCM4343W WiFi."
SECTION = "base"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PR = "r1"

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "file://bcmdhd.conf \
	   file://GPLv2.patch"

S = "${WORKDIR}"

do_compile() {
}

do_install_append_cl-som-imx7() {
	install -d ${D}${sysconfdir}/modprobe.d
	install -m 0644 ${WORKDIR}/bcmdhd.conf ${D}${sysconfdir}/modprobe.d/bcmdhd.conf
}
