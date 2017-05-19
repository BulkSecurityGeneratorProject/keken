import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Commentaire } from './commentaire.model';
import { CommentairePopupService } from './commentaire-popup.service';
import { CommentaireService } from './commentaire.service';
import { Biere, BiereService } from '../biere';
import { UserExtra, UserExtraService } from '../user-extra';

@Component({
    selector: 'jhi-commentaire-dialog',
    templateUrl: './commentaire-dialog.component.html'
})
export class CommentaireDialogComponent implements OnInit {

    commentaire: Commentaire;
    authorities: any[];
    isSaving: boolean;

    bieres: Biere[];

    userextras: UserExtra[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private commentaireService: CommentaireService,
        private biereService: BiereService,
        private userExtraService: UserExtraService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.biereService.query().subscribe(
            (res: Response) => { this.bieres = res.json(); }, (res: Response) => this.onError(res.json()));
        this.userExtraService.query().subscribe(
            (res: Response) => { this.userextras = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commentaire.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commentaireService.update(this.commentaire));
        } else {
            this.subscribeToSaveResponse(
                this.commentaireService.create(this.commentaire));
        }
    }

    private subscribeToSaveResponse(result: Observable<Commentaire>) {
        result.subscribe((res: Commentaire) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Commentaire) {
        this.eventManager.broadcast({ name: 'commentaireListModification', content: 'OK'});
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

    trackBiereById(index: number, item: Biere) {
        return item.id;
    }

    trackUserExtraById(index: number, item: UserExtra) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-commentaire-popup',
    template: ''
})
export class CommentairePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentairePopupService: CommentairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.commentairePopupService
                    .open(CommentaireDialogComponent, params['id']);
            } else {
                this.modalRef = this.commentairePopupService
                    .open(CommentaireDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
