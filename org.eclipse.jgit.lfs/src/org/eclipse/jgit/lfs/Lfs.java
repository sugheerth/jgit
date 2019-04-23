/*
 * Copyright (C) 2016, Christian Halstrick <christian.halstrick@sap.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Distribution License v1.0 which
 * accompanies this distribution, is reproduced below, and is
 * available at http://www.eclipse.org/org/documents/edl-v10.php
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 *   names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.lfs.lib.AnyLongObjectId;

/**
 * Class which represents the lfs folder hierarchy inside a .git folder
 *
 * @since 4.6
 */
public class Lfs {
	private Path root;

	private Path objDir;

	private Path tmpDir;

	/**
	 * @param root
	 *            the path to the LFS media directory. Will be "<repo>/.git/lfs"
	 */
	public Lfs(Path root) {
		this.root = root;
	}

	/**
	 * @return the path to the LFS directory
	 */
	public Path getLfsRoot() {
		return root;
	}

	/**
	 * @return the path to the temp directory used by LFS. Will be
	 *         "<repo>/.git/lfs/tmp"
	 */
	public Path getLfsTmpDir() {
		if (tmpDir == null) {
			tmpDir = root.resolve("tmp"); //$NON-NLS-1$
		}
		return tmpDir;
	}

	/**
	 * @return the path to the object directory used by LFS. Will be
	 *         "<repo>/.git/lfs/objects"
	 */
	public Path getLfsObjDir() {
		if (objDir == null) {
			objDir = root.resolve("objects"); //$NON-NLS-1$
		}
		return objDir;
	}

	/**
	 * @param id
	 *            the id of the mediafile
	 * @return the file which stores the original content. This will be files
	 *         underneath
	 *         "<repo>/.git/lfs/objects/<firstTwoLettersOfID>/<remainingLettersOfID>"
	 */
	public Path getMediaFile(AnyLongObjectId id) {
		String idStr = id.name();
		return getLfsObjDir().resolve(idStr.substring(0, 2))
				.resolve(idStr.substring(2));
	}

	/**
	 * Create a new temp file in the LFS directory
	 *
	 * @return a new temporary file in the LFS directory
	 * @throws IOException
	 *             when the temp file could not be created
	 */
	public Path createTmpFile() throws IOException {
		return Files.createTempFile(getLfsTmpDir(), null, null);
	}

}
