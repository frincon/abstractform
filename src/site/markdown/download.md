Download
========

All downloads include the binaries, the source code, and the javadocs.

0.1.1
-----

*The latest release,* released on 2014-06-02.

* [abstractform-0.1.1-bin.tar.gz](https://github.com/frincon/abstractform/releases/download/v0.1.1/abstractform-0.1.1-bin.tar.gz) (7.1 MB)
* [abstractform-0.1.1-bit.zip](https://github.com/frincon/abstractform/releases/download/v0.1.1/abstractform-0.1.1-bin.zip) (7.9 MB)

For Maven:

                <dependency>
                        <groupId>org.abstractform</groupId>
                        <artifactId>org.abstractform.core</artifactId>
                        <version>0.1.1</version>
                </dependency>
                <dependency>
                        <groupId>org.abstractform</groupId>
                        <artifactId>org.abstractform.core.fluent</artifactId>
                        <version>0.1.1</version>
                </dependency>
                <dependency>
                        <groupId>org.abstractform</groupId>
                        <artifactId>org.abstractform.vaadin</artifactId>
                        <version>0.1.1</version>
                </dependency>
                <dependency>
                        <groupId>org.abstractform</groupId>
                        <artifactId>org.abstractform.binding</artifactId>
                        <version>0.1.1</version>
                </dependency>
                <dependency>
                        <groupId>org.abstractform</groupId>
                        <artifactId>org.abstractform.binding.fluent</artifactId>
                        <version>0.1.1</version>
                </dependency>
                <dependency>
                        <groupId>org.abstractform</groupId>
                        <artifactId>org.abstractform.binding.vaadin</artifactId>
                        <version>0.1.1</version>
                </dependency>
                <dependency>
                        <groupId>org.abstractform</groupId>
                        <artifactId>org.abstractform.binding.eclipse</artifactId>
                        <version>0.1.1</version>
                </dependency>

Note that if you use the module `org.abstractform.binding.eclipse` you need to include the packages of eclipse databinding manually
because this packages are not in the Maven Centra Repository. The packages you need are the following:

* `org.eclipse.core.databinding`
* `org.eclipse.core.databinding.beans`
* `org.eclipse.core.databinding.observable`
* `org.eclipse.core.databinding.property`
* `org.eclipse.equinox.common`
* `org.eclipse.osgi`

