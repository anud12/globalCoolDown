import {Injectable} from '@angular/core';
import {RxStompService} from "@stomp/ng2-stompjs";

type StompCallback<T> = (response: T) => void

@Injectable({
    providedIn: 'root'
})
export class StompService {

    constructor(private rxStompService: RxStompService) {
    }

    publish(destination: string, body: any) {
        console.log("StompService :" + destination + ":", body)
        this.rxStompService.stompClient.publish({destination, body: body})
    }

    subscribe<T>(destination: string, callback: StompCallback<T>, error?: (error: any) => void) {
        return this.rxStompService.connected$
            .subscribe(value => {
                return this.rxStompService.stompClient.subscribe(destination, message => {
                    const object;
                    try {
                        object = JSON.parse(message.body);
                    } catch (e) {
                        object = message.body
                    }
                    callback(object);
                })

            })
    }
}
