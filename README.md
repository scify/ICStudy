ICStudy
=======

Abstract
--------
Students with severe sight conditions (partially blind), cannot absorb the educational process as well as other students: they cannot see the classroom board, or read books/notes. ICStudy is a solution developed by SciFY which helps students with such conditions attend classes and participate. It is also an open call to all developers to help, so that a useful, fully functional and stable version can be made.

Installation - Dependencies
---------------------------
1. Install Yasm (assembler disassembler for the Intel x86 architecture) by running sudo apt-get install yasm
2. Download and install ffmpeg protocol (Warning: Dn not download it from aptitude, choose this link instead: <a href="https://www.ffmpeg.org">https://www.ffmpeg.org</a>)
3. Run ICStudy - Client/runClientWin.sh (common for Windows and Linux platforms)
4. Run ICStudy - Server/getIpAndStreamLinux.sh or getIpAndStreamWin.sh, based on the server's platform.

Implementation
--------------
The ICStudy project involves both software and hardware solutions, this means we should be able to utilize hardware (interactive boards, mobile devices) and build software accordingly. The current version (Beta) is an extended implementation of [ICSee][1].

We can support filtering on feed live-streamed from another computer (through http). This partially solves the problem described in the above section, if the school uses an interactive (digital) board.

Main Technologies
-----------------
<a href="http://opencv.org/"><img src="http://upload.wikimedia.org/wikipedia/commons/thumb/3/32/OpenCV_Logo_with_text_svg_version.svg/750px-OpenCV_Logo_with_text_svg_version.svg.png" alt="OpenCV" width="100px"></a>

<a href="http://openjdk.java.net/"><img src="http://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/OpenJDK_logo.png/200px-OpenJDK_logo.png" alt="Java" width="100px"></a>

[1]: http://www.scify.gr/site/en/projects/in-progress/icsee
