# ProDevMe

## Motivations

Pro Dev Me started off as a MadLibs game. We wanted to create a game that we could 
get a laugh out of while writing code that was going to prove challenging.
After a brainstorm session, we figured out that the underlying technology
of our game could be used to address a problem a lot of people have in present day.
During our first Professional Development session in our coding bootcamp, we found 
that our resumes were no where near as well written as we thought they were.
This is where we believe Pro Dev Me can help.
People have problems getting jobs everyday and sadly, many don't even know why.
In many cases, their resume never even reached a recruiter or a hiring manager,
it was filtered out in the company's automated screening. Our aim is to help the
user get their resume past the initial screening and in front of an actual human.

## Current State

In its current state, Pro Dev Me can create, store, edit, share, and import documents.
All documents are saved in local storage unless shared to a cloud service such as
Google Drive. When sharing, the user has the option to export their document as a
plain text file, or as a pdf file. If markdown is present in the document it is 
interpreted as HTML and then saved as a PDF document.

### What is still needed

+ Scanning of the document for keywords

+ SalesForce as a backend

+ Camera integration along with OCR

### Tested Devices

+ Nexus 5X emulator (API 23) running on GenyMotion

+ Motorola Moto E (API 25)

+ Samsung Tab S2 (API 21)

### 3rd Party Libraries

+ PDFBox [https://github.com/apache/pdfbox]

+ OKHTTP [https://github.com/square/okhttp]

+ Feras MarkdownView

+ ROOM (ORM) [https://developer.android.com/topic/libraries/architecture/room.html]

### External Services

+ Google Drive

+ markdowntopdf.com

### Improvements

There aren't really specific things I would say should be improved cosmetically.
I would like to show the app to multiple people and ask what they would improve
to make the UI look nicer/cleaner.

### Stretch Goals

+ Having documents analyzed for formatting and possibly intent using Einstein
Laguage (Natural Language Processing) API.

+ Give the user the ability to upload more than resumes and cover letter.

### Third Party Libraries

+ 


**********************************************************************************
## License

Copyright &copy; 2018 Jacques Chevrier

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
