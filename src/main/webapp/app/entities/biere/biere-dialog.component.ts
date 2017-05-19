import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Biere } from './biere.model';
import { BierePopupService } from './biere-popup.service';
import { BiereService } from './biere.service';
import { UserExtra, UserExtraService } from '../user-extra';

@Component({
    selector: 'jhi-biere-dialog',
    templateUrl: './biere-dialog.component.html'
})
export class BiereDialogComponent implements OnInit {

    biere: Biere;
    authorities: any[];
    isSaving: boolean;

    userextras: UserExtra[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private biereService: BiereService,
        private userExtraService: UserExtraService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userExtraService.query().subscribe(
            (res: Response) => { this.userextras = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.biere.id !== undefined) {
            this.subscribeToSaveResponse(
                this.biereService.update(this.biere));
        } else {
            this.subscribeToSaveResponse(
                this.biereService.create(this.biere));
        }
    }

    private subscribeToSaveResponse(result: Observable<Biere>) {
        result.subscribe((res: Biere) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Biere) {
        this.eventManager.broadcast({ name: 'biereListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserExtraById(index: number, item: UserExtra) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-biere-popup',
    template: ''
})
export class BierePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bierePopupService: BierePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bierePopupService
                    .open(BiereDialogComponent, params['id']);
            } else {
                this.modalRef = this.bierePopupService
                    .open(BiereDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
