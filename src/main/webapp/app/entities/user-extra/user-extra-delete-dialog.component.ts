import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { UserExtra } from './user-extra.model';
import { UserExtraPopupService } from './user-extra-popup.service';
import { UserExtraService } from './user-extra.service';

@Component({
    selector: 'jhi-user-extra-delete-dialog',
    templateUrl: './user-extra-delete-dialog.component.html'
})
export class UserExtraDeleteDialogComponent {

    userExtra: UserExtra;

    constructor(
        private userExtraService: UserExtraService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userExtraService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userExtraListModification',
                content: 'Deleted an userExtra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-extra-delete-popup',
    template: ''
})
export class UserExtraDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userExtraPopupService: UserExtraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.userExtraPopupService
                .open(UserExtraDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
