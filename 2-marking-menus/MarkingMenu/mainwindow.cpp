#include "mainwindow.h"
#include <QVBoxLayout>
#include <QPushButton>

#include "markingmenu.h"
#include "radialmenu.h"

MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent) {
    this->resize(QSize(1000,1000));

    QVBoxLayout* layout = new QVBoxLayout();
    QWidget* central = new QWidget(this);
    central->setLayout(layout);

    /*
    marking_menu = new MarkingMenu();
    layout->addWidget(marking_menu);
    */
    radial_menu = new RadialMenu();
    RadialMenu* sub_menu = new RadialMenu();
    radial_menu->addEntry("sub menu 1" , sub_menu);
    radial_menu->addEntry("action 2", new std::function<void(void)>([](){qDebug("action 2");}));
    radial_menu->addEntry("action 3", new std::function<void(void)>([](){qDebug("action 3");}));
    radial_menu->addEntry("action 4", new std::function<void(void)>([](){qDebug("action 4");}));
    sub_menu->addEntry("action 11", new std::function<void(void)>([](){qDebug("action 11");}));


    layout->addWidget(radial_menu);

    setCentralWidget(central);
}

MainWindow::~MainWindow() {

    delete radial_menu;
}
