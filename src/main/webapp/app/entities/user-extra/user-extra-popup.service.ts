import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UserExtra } from './user-extra.model';
import { UserExtraService } from './user-extra.service';
@Injectable()
export class UserExtraPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private userExtraService: UserExtraService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.userExtraService.find(id).subscribe((userExtra) => {
                this.userExtraModalRef(component, userExtra);
            });
        } else {
            return this.userExtraModalRef(component, new UserExtra());
        }
    }

    userExtraModalRef(component: Component, userExtra: UserExtra): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userExtra = userExtra;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
