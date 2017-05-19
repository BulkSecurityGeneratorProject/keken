import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Biere } from './biere.model';
import { BierePopupService } from './biere-popup.service';
import { BiereService } from './biere.service';

@Component({
    selector: 'jhi-biere-delete-dialog',
    templateUrl: './biere-delete-dialog.component.html'
})
export class BiereDeleteDialogComponent {

    biere: Biere;

    constructor(
        private biereService: BiereService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.biereService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'biereListModification',
                content: 'Deleted an biere'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-biere-delete-popup',
    template: ''
})
export class BiereDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bierePopupService: BierePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bierePopupService
                .open(BiereDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
