package com.rk.df;

import com.rk.df.checksum.Checksum;
import com.rk.df.checksum.Sha256ChecksumImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ChecksumTest {

    @Test
    public void testChecksum() throws IOException {

        Checksum checksum = new Sha256ChecksumImpl();
        String checksumValue = checksum.generateChecksum(new File("src/test/resources/test.file"));
        Assert.assertNotNull(checksumValue);
        Assert.assertEquals("c934d89b14571946a4714361aa7100e086a489503cd86484cb21168d156f0625", checksumValue);
    }

    @Test(expected = IOException.class)
    public void testChecksumError() throws IOException {
        Checksum checksum = new Sha256ChecksumImpl();
        String checksumValue = checksum.generateChecksum(new File("test.file"));
        Assert.assertNotNull(checksumValue);
    }

    public void testRecursiveFile() {
        Checksum checksum = new Sha256ChecksumImpl();
//        checksum.
    }
}
