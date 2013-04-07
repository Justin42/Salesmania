#! /bin/bash
if [ "$1" == --release ];
then
	VERSION=`git describe --tags --abbrev=0`
else
	TYPE=`git describe --tags | sed -e 's/-.*/-dev/'`
	REVISION=`git describe --tags | sed -e 's/.*[a-zA-Z]-//' -e 's/-.*//'`
	SHA=`git rev-parse HEAD | cut -c1-8`
	VERSION="${TYPE}_r${REVISION}(${SHA})";
fi
echo $VERSION

