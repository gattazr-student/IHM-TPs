#include "radialmenu.h"
#include "QMouseEvent"

RadialMenu::RadialMenu(QWidget *parent) : QWidget(parent) {
}

QLabel* RadialMenu::createLabel(const std::string& content) {
    QLabel* label = new QLabel(this);
    label->setText(QString::fromStdString(content));
    label->setAutoFillBackground(false);
    label->setStyleSheet(QLatin1String("font: 75 10pt \"Calibri\";\n" "font-weight: bold;\n"));
    label->setAlignment(Qt::AlignCenter);
    label->setFrameShape(QFrame::StyledPanel);
    label->setFrameShadow(QFrame::Sunken);

    label->resize(QSize(content.size()*10, 25));
    label->hide();

    return label;
}

void RadialMenu::addEntry(std::string name, std::function<void(void)>* action){
    QLabel* label = createLabel(name);
    menu_entry entry = {label, menu_entry::ACTION, {}};
    menu_entries.push_back(entry);
}

void RadialMenu::addEntry(std::string name, RadialMenu* menu){
    QLabel* label = createLabel(name);
    menu_entry entry = {label, menu_entry::MENU, {menu}};
    menu_entries.push_back(entry);
}

void RadialMenu::showMenu(const QPoint& position) {

    float angle = 90.f;
    QPoint point_orig(position.x() + distance, position.y());
    for (unsigned int i=0; i < menu_entries.size(); i++) {
        menu_entries[i].label->move(point_orig.x() - menu_entries[i].label->size().width()/2,
                                    point_orig.y() - menu_entries[i].label->size().height()/2);
        menu_entries[i].label->show();

        if (i%4 == 3 && i > 0){
            point_orig = rotationGlobal(point_orig, position, -45);
        }
        point_orig = rotationGlobal(point_orig, position, -angle);
    }

    this->show();
}

void RadialMenu::hideMenu(){
    for (unsigned int i=0; i < menu_entries.size(); i++) {
        menu_entries[i].label->hide();
    }
}

void RadialMenu::mouseMoveEvent(QMouseEvent* qm){

}

void RadialMenu::mousePressEvent(QMouseEvent* qm) {
    if (qm->button() == Qt::RightButton) {
        qDebug("Right click");
        showMenu(qm->pos());
    }else{
        qDebug("Left click");
    }
}

void RadialMenu::mouseReleaseEvent(QMouseEvent* qm) {
    if (qm->button() == Qt::RightButton) {
        qDebug("Right Release");
        hideMenu();
    }else{
        qDebug("Left Release");
    }
}

