/**********************************************************************************
*  MagicBox V0.1
*  By Jack Huo - http://www.kingyar.com
*  4/23/2008
*
*  For more information on this script: 
*  http://magicbox.kingyar.com
* 
*  Licensed under the Creative Commons Attribution 3.0 License: 
*  http://creativecommons.org/licenses/by/3.0/
**********************************************************************************/

if (null == MagicBoxSystem)
{
    var MagicBoxSystem = {
        /*size*/
        ContainerSize : {width : 480, height : 360},
        ToolboxSize : {width : 100, height : 60},
        ThumbImageSize : {width : 80, height : 50},
        StatusBarSize : {height : 20},
        Spacing : 8,
        
        /*language*/
        L_IMAGE_LOADING : "正在加载图片",
        L_IMAGE_CURRENT : "当前图片",

        magicBoxList : [], 
        magicBoxNum : 0,
        initialize : function()
        {
            if (typeof Prototype == "undefined") {
                throw "error : magicbox needs prototype.js";
            }
            
            this.start();
        },
        start : function()
        {
            var containerList = $$(".magicbox");
            if (null == containerList)
            {
                return;
            }
            
            this.magicBoxNum = containerList.length;
            for (var i = 0; i < containerList.length; ++i)
            {
                this.createMagicBox(containerList[i]);                
            }
        },
        createMagicBox : function(container)
        {
            if (!container || !container.getElementsByTagName)
            {
                return;
            }
            
            var imgList = container.getElementsByTagName("span");
            if (!imgList)
            {
                return;
            }
            var imgFileList = [];
            $A(imgList).each(
                function (img) 
                {
                    if (img) 
                    {
                        imgFileList.push({src : img.getAttribute("src")});
                    }                    
                }
            );
            
            var magicBox = new MagicBox(container, imgFileList);
            this.magicBoxList.push(magicBox);
        }
    };

    /*
    *  MagicBox System init
    */
    Event.observe(window, "load", MagicBoxSystem.initialize.bind(MagicBoxSystem));

    /*
    * MagicBox class
    */
    var MagicBox = Class.create({
        initialize : function(container, imgFileList)
        {
            if (!container) return;
            if (!imgFileList || !imgFileList.length || imgFileList.length <= 0) return;                
            
            //get container size
            container = $(container);
            var containerWidth = MagicBoxSystem.ContainerSize.width;
            var containerHeight = MagicBoxSystem.ContainerSize.height;
            if (container.getAttribute("width")) {
                containerWidth = Math.round(parseFloat(container.getAttribute("width")));
            }
            if (container.getAttribute("height")) {
                containerHeight = Math.round(parseFloat(container.getAttribute("height")));
            }
            container.setAttribute("width", containerWidth);
            container.setAttribute("height", containerHeight);     

            //save data
            this.origContainer = container;
            this.imgFileList = imgFileList;
            this.imgList = [];
            this.thumbImageList = [];
            this.indexImageActive = -1;
            this.fullScreenMode = false;

            this.create(container, imgFileList);        
        },
        
        /*create magicbox*/
        create : function(container, imgFileList)
        {
            if (!container) return;
            if (!imgFileList || !imgFileList.length || imgFileList.length <= 0) return;
            
            var owner = this;
            this.offsetThumbBox = 0;
            
            //get container size
            var containerWidth = MagicBoxSystem.ContainerSize.width;
            var containerHeight = MagicBoxSystem.ContainerSize.height;
            if (container.getAttribute("width")) {
                containerWidth = Math.round(parseFloat(container.getAttribute("width")));
            }
            if (container.getAttribute("height")) {
                containerHeight = Math.round(parseFloat(container.getAttribute("height")));
            }

            //get component size
            var containerSize = {width:containerWidth, height:containerHeight};
            var toolboxSize = MagicBoxSystem.ToolboxSize;
            var thumbImageSize = MagicBoxSystem.ThumbImageSize;
            var statusBarSize = MagicBoxSystem.StatusBarSize;
            var spacing = MagicBoxSystem.Spacing;
                    
            //clear container
            container = $(container);
            container.innerHTML = "";    
            container.style.width = containerSize.width + "px";
            container.style.height = containerSize.height + "px";            
            this.container = container;      
            
            //create screen
            var screenWidth = containerSize.width - spacing;
            var screenHeight = containerSize.height - toolboxSize.height - statusBarSize.height - spacing;        
            
            var screen = new Element("div");
            screen.addClassName("screen");
            screen.style.position = "relative";
            screen.style.width = screenWidth + "px";
            screen.style.height = screenHeight + "px";
            screen.style.top = Math.floor(spacing / 2) + "px";                
            screen.ondblclick = function() {
                if (owner.fullScreenMode)
                {
                    owner.onEscFullScreen();
                }
                else 
                {
                    owner.onFullScreen();
                }
            }
            container.appendChild(screen);
            this.screen = screen;
            
            //create footer
            var footerWidth = containerSize.width - spacing;
            var footerHeight = toolboxSize.height;
            
            var footer = new Element("div");
            footer.addClassName("footer");
            footer.style.width = footerWidth + "px";
            footer.style.height = footerHeight + "px";
            container.appendChild(footer);

            //create thumbContainer
            var thumbContainerWidth = containerSize.width - toolboxSize.width - spacing * 2;
            var thumbContainerHeight = toolboxSize.height;
            
            var thumbContainer = new Element("div");
            thumbContainer.addClassName("thumbContainer");
            thumbContainer.style.width = thumbContainerWidth + "px";
            thumbContainer.style.height = thumbContainerHeight + "px";
            footer.appendChild(thumbContainer);
            this.thumbContainer = thumbContainer;
            
            //create thumbBox
            var thumbboxWidth = Math.ceil((thumbImageSize.width + spacing/2) * this.imgFileList.length);
            var thumbboxHeight = toolboxSize.height;
            
            var thumbBox = new Element("div");
            thumbBox.addClassName("thumbBox");
            thumbBox.style.width = thumbboxWidth + "px";
            thumbBox.style.height = thumbboxHeight + "px";
            thumbBox.style.overflow = "hidden";
            thumbContainer.appendChild(thumbBox);
            this.thumbBox = thumbBox;

            //create toolBox        
            var toolBox = new Element("div");
            toolBox.addClassName("toolBox");
            toolBox.style.width = (toolboxSize.width - spacing) + "px";
            toolBox.style.height = toolboxSize.height + "px";
            footer.appendChild(toolBox);
            
            //create buttons
            var btnWidth = Math.floor(toolboxSize.width * 0.3);
            var btnHeight = toolboxSize.height;
            
            var btnPrevImage = new Element("div");          //create btn PrevImage
            btnPrevImage.addClassName("btnPrevImage");
            btnPrevImage.style.width = btnWidth + "px";
            btnPrevImage.style.height = btnHeight + "px";
            btnPrevImage.onclick = function() {
                owner.onPrevImage();
            };
            toolBox.appendChild(btnPrevImage);

            var btnNextImage = new Element("div");          //create btn NextImage
            btnNextImage.addClassName("btnNextImage");
            btnNextImage.style.width = btnWidth + "px";
            btnNextImage.style.height = btnHeight + "px";
            btnNextImage.onclick = function() {
                owner.onNextImage();
            };
            toolBox.appendChild(btnNextImage);
            
            var btnFullScreen = new Element("div");         //create btn FullScreen
            btnFullScreen.style.width = btnWidth + "px";
            btnFullScreen.style.height = btnHeight + "px";
            if (!this.fullScreenMode)
            {
                btnFullScreen.className = "btnFullScreen";
                btnFullScreen.onclick = function(){
                    owner.onFullScreen();
                };            
            }
            else 
            {
                btnFullScreen.className = "btnQuitFullScreen";
                btnFullScreen.onclick = function(){
                    owner.onEscFullScreen();
                };            
            }
            toolBox.appendChild(btnFullScreen);        
            
            //create statusBar
            var statusBar = new Element("div");
            statusBar.addClassName("statusBar");        
            statusBar.style.width = (containerSize.width - spacing) + "px";
            statusBar.style.height = (statusBarSize.height - spacing) +"px";
            container.appendChild(statusBar);
            this.statusBar = statusBar;

            //load image
            container.style.display = "block";
            this.screen.update(this.createLoadingBox(this.screen));
            this.loadImage();
            
            //bind keydown listener
            if (MagicBoxSystem.magicBoxNum == 1 || this.fullScreenMode) {
                this.onKeyDown = this.onKeyDown.bindAsEventListener(this);
                document.observe("keydown", this.onKeyDown);
            }
        },
        
        /*load image*/
        //中间大图的加载
        loadImage : function()
        {
            var owner = this;
            
            if (this.imgList.length < this.imgFileList.length)
            {
                if (this.thumbImageList.length > 0)
                {
                    $A(this.thumbImageList).each(
                        function(item) {
                            owner.thumbBox.appendChild(item);
                        }
                    );
                    
                    var indexImageActive = this.indexImageActive;
                    this.indexImageActive = -1;
                    if (indexImageActive < 0)
                    {
                        this.showImage(0);
                    }
                    else if (indexImageActive < this.imgList.length)
                    {
                        this.showImage(indexImageActive);
                    }
                }
                this.preLoadImage();
            }
            else 
            {
                this.loadThumbImage();
            }
        },
        loadThumbImage : function()
        {
            var indexImageActive = this.indexImageActive;
            this.indexImageActive = -1;
            for (var i = 0; i < this.thumbImageList.length; ++i)
            {
                this.thumbBox.appendChild(this.thumbImageList[i]);
                if (i == indexImageActive)
                {
                    this.showImage(indexImageActive);
                }
            }
        },
        preLoadImage : function()
        {
            var owner = this;
            
            //get index of image should be load
            var indexImageLoad = this.imgList.length;
            if (indexImageLoad >= this.imgFileList.length)
            {
                this.showStatus(MagicBoxSystem.L_IMAGE_CURRENT + ": [" + (this.indexImageActive + 1) + "/" + this.thumbImageList.length + "]");
                return;
            }
            this.showStatus(MagicBoxSystem.L_IMAGE_LOADING + ": [" + (indexImageLoad + 1) + "/" + this.imgFileList.length + "] ...");
            
            //get image file path
            var newImgFilePath = this.imgFileList[indexImageLoad].src;
            if (!newImgFilePath) return;
            
            //load image
            var newImage = new Image();
            newImage.onload = function(){
                owner.onPreImageLoad(newImage);
            };
            newImage.src = newImgFilePath;
        },
        onPreImageLoad : function(image)
        {
            //check duplicate image
            var ret = $A(this.imgList).find(
                function(img){
                    if (img.src == image.src) return img;
                }
            );
            if (ret) return;

            //add to imgList
            this.createThumbImage(this.imgList.length, image);
            this.imgList.push(image);
            if (this.imgList.length == 1)
            {
                this.showImage(0);
            }
            else
            {
                this.showImage(this.indexImageActive);
            }

            this.preLoadImage();
        },
        
        createThumbImage : function(index, image)
        {
            var owner = this;
            
            //create new thumbnail
            var thumbImageSize = MagicBoxSystem.ThumbImageSize;
            var imageWidth = thumbImageSize.width;
            var imageHeight = thumbImageSize.height;
            
            var thumbImage = new Element("div");
            thumbImage.addClassName("thumbImage");
            thumbImage.style.width = imageWidth + "px";
            thumbImage.style.height = imageHeight + "px";
            thumbImage.style.marginTop = (Math.floor((this.thumbBox.getDimensions().height - imageHeight)/2) - 2) + "px";
            thumbImage.setOpacity(0.6);
            thumbImage.onclick = function()
            {
                owner.showImage(index);
            }
            thumbImage.onmouseover = function()
            {
                this.setOpacity(1);
            }
            thumbImage.onmouseout = function()
            {
                if (owner.indexImageActive != index)
                {
                    this.setOpacity(0.6);
                }
            }
            
            var newImage = new Image();
            newImage.src = image.src;
            newImage.style.width = (imageWidth - 2) + "px";
            newImage.style.height = (imageHeight - 2) + "px";
            newImage.style.marginTop = "1px";
            thumbImage.appendChild(newImage);
            this.thumbBox.appendChild(thumbImage);  
            
            this.thumbImageList.push(thumbImage);      
        },
        createLoadingBox : function(parent)
        {
            var parentSize = $(parent).getDimensions();
            
            var elem = new Element("div");
            elem.addClassName("loadingbox");
            elem.style.width = parentSize.width + "px";
            elem.style.height = parentSize.height + "px";
            elem.innerHTML = "&nbsp;";
            return elem;
        },

        showImage : function(index)
        {
            var image = this.imgList[index];
            if (!image) return;        

            if (index != this.indexImageActive)
            {
                var screenSize = this.screen.getDimensions();           
                var xRate = (image.width + 8) / screenSize.width;
                var yRate = (image.height + 8) / screenSize.height;
                var rate = Math.max(xRate, yRate);

                var imageWidth = image.width;
                var imageHeight = image.height;
                if (rate > 1)
                {
                    imageWidth = Math.floor(imageWidth / rate);
                    imageHeight = Math.floor(imageHeight / rate);
                }
                        
                this.screen.innerHTML = "";
                var newImage = new Image();
                newImage.src = image.src;
                newImage.style.width = imageWidth + "px";
                newImage.style.height = imageHeight + "px";
                newImage.style.position = "relative";
                newImage.style.top = Math.floor(Math.max(0, (screenSize.height - imageHeight - 4) / 2)) + "px";
                newImage.style.display = "none";
                this.screen.update(newImage);        
                new Effect.Appear(newImage, {duration:0.8});

                if (this.indexImageActive >= 0)
                {
                    this.thumbImageList[this.indexImageActive].setOpacity(0.6);
                    this.thumbImageList[this.indexImageActive].removeClassName("current");
                }
                this.thumbImageList[index].setOpacity(1);
                this.thumbImageList[index].addClassName("current");
                this.indexImageActive = index;
                
                var status = MagicBoxSystem.L_IMAGE_CURRENT + "：[" + (index + 1) + "/" + this.imgFileList.length + "]";
                this.showStatus(status);
            }
            
            this.slideThumbBox();
        },
        slideThumbBox : function()
        {
            var thumbImageNum = this.thumbImageList.length;
            var thumbImageIndex = this.indexImageActive;
            var thumbImageSize = MagicBoxSystem.ThumbImageSize;
            var thumbContainerSize = this.thumbContainer.getDimensions();
            var thumbBoxSize = this.thumbBox.getDimensions();
            var thumbBoxOffset = this.offsetThumbBox;
            
            if (thumbBoxSize.width <= thumbContainerSize.width)
            {
                return;
            }

            var thumbImageCenter = (thumbImageIndex + 0.5) * thumbImageSize.width - thumbBoxOffset;
            
            var offset = 0;
            if (this.indexImageActive == 0)
            {
                offset = -this.offsetThumbBox;    
            }
            else 
            {
                if (thumbImageCenter > (thumbContainerSize.width / 2))
                {
                    offset = Math.min(thumbBoxSize.width - thumbBoxOffset - thumbContainerSize.width, thumbImageCenter - thumbContainerSize.width / 2);
                }
                else
                {
                    offset = -Math.min(thumbContainerSize.width / 2 - thumbImageCenter, thumbBoxOffset);
                }
                if (thumbImageIndex == (this.imgList.length - 1))
                {
                    offset = thumbBoxSize.width - thumbContainerSize.width - thumbBoxOffset;
                }
            }
            if (Math.abs(offset) >= 1)
            {
                this.offsetThumbBox += offset;
                if (this.imgList.length < this.imgFileList.length)
                {
                    this.thumbBox.style.position = "relative";
                    this.thumbBox.style.left = -this.offsetThumbBox + "px";
                }
                else
                {
                    new Effect.Move(this.thumbBox, {x:-offset, duration:0.2});
                }
            }        
        },
        showStatus : function(elem)
        {
            if (!this.statusBar)
            {
                throw "sys error: invalid statusBar";
            }
            
            this.statusBar.update(elem);
        },

        /*event handler*/
        onPrevImage : function()
        {
            if (this.thumbImageList.length <= 0) return;

            var index = this.indexImageActive - 1;
            if (index < 0)
            {
                index = this.thumbImageList.length - 1;
            }
            this.showImage(index);
        },
        onNextImage : function()
        {
            if (this.thumbImageList.length <= 0) return;
            
            var index = this.indexImageActive + 1;
            if (index >= this.thumbImageList.length)
            {
                index = 0;
            }
            this.showImage(index);
        },
        onFirstImage : function()
        {
            if (this.thumbImageList.length <= 0) return;

            if (this.imgList.length > 0)
            {
                this.showImage(0);
            }
        },
        onEndImage : function()
        {
            if (this.thumbImageList.length <= 0) return;

            if (this.imgList.length > 0)
            {
                this.showImage(this.imgList.length - 1);
            }
        },
        onFullScreen : function()
        {           
            var spacing = MagicBoxSystem.Spacing;
            
            //create background
            var background = new Element("div");
            background.addClassName("fullScreen");
            background.style.position = "absolute";
            background.style.width = Utility.getWindowWidth() + "px";
            background.style.height = Utility.getPageHeight() + "px";
            background.style.left = "0px";
            background.style.top = "0px";
            document.body.appendChild(background);
            this.background = background;

            //create fullScreen container
            var container = new Element("div");
            container.addClassName("magicbox");
            container.style.position = "absolute";
            container.setAttribute("width", Utility.getWindowWidth() - spacing);
            container.setAttribute("height", Utility.getWindowHeight() - spacing);
            container.style.left = Math.floor(spacing / 2) + "px";
            container.style.top =  Math.floor(Utility.getScrollY() + spacing / 2) + "px";
            background.appendChild(container);
            
            document.stopObserving("keydown", this.onKeyDown);
            this.fullScreenMode = true;
            this.create(container, this.imgFileList);
        },
        onEscFullScreen : function()
        {
            document.body.removeChild(this.background);
            document.stopObserving("keydown", this.onKeyDown);
            
            this.fullScreenMode = false;
            this.create(this.origContainer, this.imgFileList); 
        },
        onKeyDown : function(e)
        {
            var keyCode = e.keyCode;
            switch (keyCode)
            {
                case Event.KEY_ESC:
                    if (this.fullScreenMode) 
                    {
                        this.onEscFullScreen();
                    }
                    break;
                case Event.KEY_LEFT:
                    this.onPrevImage();
                    break;
                case Event.KEY_RIGHT:
                    this.onNextImage();
                    break;
            }
        }
    });
}

